package com.theodorosidmar.authservice.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.provider.ClientDetails
import kotlin.collections.HashSet

@Document
class AuthClientDetails(
    private val clientId: String,
    private val clientSecret: String,
    private val scopes: String,
    private val grantTypes: String
): ClientDetails {
    private val serialVersionUID = 1L

    @Id
    private var id: String? = null

    private val resources: String? = null

    private val redirectUris: String? = null

    private val accessTokenValidity: Int? = null

    private val refreshTokenValidity: Int? = null

    private val additionalInformation: String? = null

    override fun isSecretRequired(): Boolean = true

    override fun getAdditionalInformation(): MutableMap<String, Any>? = null

    override fun getAccessTokenValiditySeconds(): Int? = accessTokenValidity

    override fun getRefreshTokenValiditySeconds(): Int? = refreshTokenValidity

    override fun getResourceIds(): Set<String>? =
        if (resources != null) HashSet(resources.split(",")) else null

    override fun getClientId(): String? = clientId

    override fun isAutoApprove(scope: String?): Boolean = true

    override fun getAuthorities(): Collection<GrantedAuthority> = arrayListOf()

    override fun getClientSecret(): String? = clientSecret

    override fun getRegisteredRedirectUri(): Set<String>? =
        if (redirectUris != null) HashSet(redirectUris.split(",")) else null

    override fun isScoped(): Boolean = false

    override fun getScope(): Set<String>? = HashSet(scopes.split(","))

    override fun getAuthorizedGrantTypes(): Set<String>? = HashSet(grantTypes.split(","))
}
