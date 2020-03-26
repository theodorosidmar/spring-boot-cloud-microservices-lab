package com.theodorosidmar.authservice.configuration

import com.theodorosidmar.authservice.configuration.mongo.MongoTokenStore
import com.theodorosidmar.authservice.service.AuthClientDetailsService
import com.theodorosidmar.authservice.service.CustomUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.TokenStore

@Configuration
@EnableAuthorizationServer
class OAuth2AuthorizationConfig : AuthorizationServerConfigurerAdapter() {
    @Autowired
    @Qualifier("authenticationManagerBean")
    private val authenticationManager: AuthenticationManager? = null
    @Autowired
    private val userDetailsService: CustomUserDetailsService? = null
    @Autowired
    private val authClientDetailsService: AuthClientDetailsService? = null
    @Autowired
    private val encoder: PasswordEncoder? = null

    @Throws(Exception::class)
    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.withClientDetails(authClientDetailsService)
    }

    @Bean
    fun tokenStore(): TokenStore = MongoTokenStore()

    @Throws(Exception::class)
    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints
            .tokenStore(tokenStore())
            .authenticationManager(authenticationManager)
            .userDetailsService(userDetailsService)
    }

    @Throws(Exception::class)
    override fun configure(oauthServer: AuthorizationServerSecurityConfigurer) {
        oauthServer
            .tokenKeyAccess("permitAll()")
            .checkTokenAccess("isAuthenticated()")
            .passwordEncoder(encoder)
            .allowFormAuthenticationForClients()
    }
}
