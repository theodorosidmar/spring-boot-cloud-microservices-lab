package com.theodorosidmar.authservice.domain

import com.theodorosidmar.authservice.utils.SerializableObjectConverter
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.oauth2.common.OAuth2RefreshToken
import org.springframework.security.oauth2.provider.OAuth2Authentication

@Document
class MongoRefreshToken(
    val token: OAuth2RefreshToken,
    val tokenId: String
) {
    @Id
    var id: String? = null
    var authentication: String? = null

    companion object {
        const val TOKEN_ID = "tokenId"
    }

    fun getAuthentication(): OAuth2Authentication? = SerializableObjectConverter.deserialize(authentication)

    fun setAuthentication(authentication: OAuth2Authentication?) {
        this.authentication = SerializableObjectConverter.serialize(authentication)
    }
}
