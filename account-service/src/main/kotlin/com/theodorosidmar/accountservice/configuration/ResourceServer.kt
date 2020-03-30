package com.theodorosidmar.accountservice.configuration

import feign.RequestInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.client.OAuth2ClientContext
import org.springframework.security.oauth2.client.OAuth2RestOperations
import org.springframework.security.oauth2.client.OAuth2RestTemplate
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices

@Configuration
@EnableResourceServer
class ResourceServerConfig @Autowired constructor(
    private val sso: ResourceServerProperties,
    private val oAuth2ClientContext: OAuth2ClientContext
) : ResourceServerConfigurerAdapter() {
    @Bean
    @ConfigurationProperties(prefix = "security.oauth2.client")
    fun clientCredentialsResourceDetails(): ClientCredentialsResourceDetails = ClientCredentialsResourceDetails()

    @Bean
    fun oauth2FeignRequestInterceptor(): RequestInterceptor =
        OAuth2FeignRequestInterceptor(oAuth2ClientContext, clientCredentialsResourceDetails())

    @Bean
    fun restTemplate(oauth2ClientContext: OAuth2ClientContext?): OAuth2RestOperations =
        OAuth2RestTemplate(clientCredentialsResourceDetails(), oauth2ClientContext)

    @Bean
    @Primary
    fun resourceServerTokenServices(): ResourceServerTokenServices =
        UserInfoTokenServices(sso.userInfoUri, sso.clientId)

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
            .antMatchers(HttpMethod.POST, "/").permitAll()
            .antMatchers(HttpMethod.OPTIONS, "/").permitAll()
            .anyRequest().authenticated()
    }
}
