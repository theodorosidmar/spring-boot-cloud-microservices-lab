package com.theodorosidmar.authservice.configuration.mongo

import com.theodorosidmar.authservice.domain.MongoAccessToken
import com.theodorosidmar.authservice.domain.MongoRefreshToken
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.common.OAuth2RefreshToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator
import org.springframework.security.oauth2.provider.token.TokenStore
import java.io.UnsupportedEncodingException
import java.math.BigInteger
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class MongoTokenStore: TokenStore {
    private val authenticationKeyGenerator: AuthenticationKeyGenerator = DefaultAuthenticationKeyGenerator()

    @Autowired
    private val mongoTemplate: MongoTemplate? = null

    override fun readAuthentication(accessToken: OAuth2AccessToken): OAuth2Authentication? =
            readAuthentication(accessToken.value)

    override fun readAuthentication(token: String): OAuth2Authentication? {
        val query = Query()
        query.addCriteria(Criteria.where(MongoAccessToken.TOKEN_ID).`is`(extractTokenKey(token)))

        return mongoTemplate?.findOne(query, MongoAccessToken::class.java)?.getAuthentication()
    }

    override fun storeAccessToken(accessToken: OAuth2AccessToken, authentication: OAuth2Authentication) {
        var refreshToken: String? = null
        if (accessToken.refreshToken != null) {
            refreshToken = accessToken.refreshToken.value
        }
        if (readAccessToken(accessToken.value) != null) {
            removeAccessToken(accessToken)
        }
        MongoAccessToken(
                tokenId = extractTokenKey(accessToken.value)!!,
                token = accessToken,
                authenticationId = authenticationKeyGenerator.extractKey(authentication),
                username = if (authentication.isClientOnly) null else authentication.name,
                clientId = authentication.oAuth2Request.clientId,
                refreshToken = extractTokenKey(refreshToken)
        ).apply {
            setAuthentication(authentication)
        }.run {
            mongoTemplate!!.save(this)
        }
    }

    override fun readAccessToken(tokenValue: String?): OAuth2AccessToken? {
        val query = Query()
        query.addCriteria(Criteria.where(MongoAccessToken.TOKEN_ID).`is`(extractTokenKey(tokenValue)))
        val mongoAccessToken = mongoTemplate!!.findOne(query, MongoAccessToken::class.java)
        return mongoAccessToken?.token
    }

    override fun removeAccessToken(oAuth2AccessToken: OAuth2AccessToken) {
        val query = Query()
        query.addCriteria(Criteria.where(MongoAccessToken.TOKEN_ID).`is`(extractTokenKey(oAuth2AccessToken.value)))
        mongoTemplate?.remove(query, MongoAccessToken::class.java)
    }

    override fun storeRefreshToken(refreshToken: OAuth2RefreshToken, authentication: OAuth2Authentication ) {
        MongoRefreshToken(token = refreshToken, tokenId = extractTokenKey(refreshToken.value)!!).apply {
            setAuthentication(authentication)
        }.let {
            mongoTemplate?.save(it)
        }
    }

    override fun readRefreshToken(tokenValue: String?): OAuth2RefreshToken? = Query().apply {
        addCriteria(Criteria.where(MongoRefreshToken.TOKEN_ID).`is`(extractTokenKey(tokenValue)))
    }.let {
        mongoTemplate?.findOne(it, MongoRefreshToken::class.java)?.token
    }

    override fun readAuthenticationForRefreshToken(refreshToken: OAuth2RefreshToken): OAuth2Authentication? =
            Query().apply {
                addCriteria(Criteria.where(MongoRefreshToken.TOKEN_ID).`is`(extractTokenKey(refreshToken.value)))
            }.let {
                return mongoTemplate?.findOne(it, MongoRefreshToken::class.java)?.getAuthentication()
            }

    override fun removeRefreshToken(refreshToken: OAuth2RefreshToken) {
        Query().apply {
            addCriteria(Criteria.where(MongoRefreshToken.TOKEN_ID).`is`(extractTokenKey(refreshToken.value)))
        }.let {
            mongoTemplate?.remove(it, MongoRefreshToken::class.java)
        }
    }

    override fun removeAccessTokenUsingRefreshToken(refreshToken: OAuth2RefreshToken) {
        Query().apply {
            addCriteria(Criteria.where(MongoAccessToken.REFRESH_TOKEN).`is`(extractTokenKey(refreshToken.value)))
        }.let {
            mongoTemplate?.remove(it, MongoAccessToken::class.java)
        }
    }

    override fun getAccessToken(authentication: OAuth2Authentication?): OAuth2AccessToken? {
        var accessToken: OAuth2AccessToken? = null
        val authenticationId = authenticationKeyGenerator.extractKey(authentication)
        val query = Query()
        query.addCriteria(Criteria.where(MongoAccessToken.AUTHENTICATION_ID).`is`(authenticationId))
        val mongoAccessToken = mongoTemplate!!.findOne(query, MongoAccessToken::class.java)
        if (mongoAccessToken != null) {
            accessToken = mongoAccessToken.token
            if (accessToken != null && authenticationId != authenticationKeyGenerator.extractKey(this.readAuthentication(accessToken))) {
                removeAccessToken(accessToken)
                storeAccessToken(accessToken, authentication!!)
            }
        }
        return accessToken
    }

    override fun findTokensByClientIdAndUserName(clientId: String?, username: String?): Collection<OAuth2AccessToken?>? =
            findTokensByCriteria(
                    Criteria.where(MongoAccessToken.CLIENT_ID).`is`(clientId)
                            .and(MongoAccessToken.USER_NAME).`is`(username))

    override fun findTokensByClientId(clientId: String?): Collection<OAuth2AccessToken?>? =
            findTokensByCriteria(Criteria.where(MongoAccessToken.CLIENT_ID).`is`(clientId))

    private fun findTokensByCriteria(criteria: Criteria): Collection<OAuth2AccessToken?>? {
        val tokens: MutableCollection<OAuth2AccessToken?> = ArrayList()
        val query = Query()
        query.addCriteria(criteria)
        val accessTokens = mongoTemplate!!.find(query, MongoAccessToken::class.java)
        for (accessToken in accessTokens) {
            tokens.add(accessToken.token)
        }
        return tokens
    }

    private fun extractTokenKey(value: String?): String? = if (value == null) {
        null
    } else {
        val digest: MessageDigest = try {
            MessageDigest.getInstance("MD5")
        } catch (var5: NoSuchAlgorithmException) {
            throw IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).")
        }
        try {
            val e = digest.digest(value.toByteArray(StandardCharsets.UTF_8))
            java.lang.String.format("%032x", BigInteger(1, e))
        } catch (var4: UnsupportedEncodingException) {
            throw IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).")
        }
    }
}
