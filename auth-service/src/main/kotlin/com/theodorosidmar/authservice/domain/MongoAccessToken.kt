package com.theodorosidmar.authservice.domain

import com.theodorosidmar.authservice.utils.SerializableObjectConverter
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.OAuth2Authentication

@Document
class MongoAccessToken(
    @Id
    val id: String? = null,
    val tokenId: String,
    var token: OAuth2AccessToken? = null,
    var authenticationId: String? = null,
    var username: String? = null,
    var clientId: String? = null,
    var refreshToken: String? = null,
    private var authentication: String? = null
) {
    companion object {
        val TOKEN_ID = "tokenId"
        val REFRESH_TOKEN = "refreshToken"
        val AUTHENTICATION_ID = "authenticationId"
        val CLIENT_ID = "clientId"
        val USER_NAME = "username"
    }

    fun getAuthentication(): OAuth2Authentication? = SerializableObjectConverter.deserialize(authentication)

    fun setAuthentication(authentication: OAuth2Authentication?) {
        this.authentication = SerializableObjectConverter.serialize(authentication)
    }
}
