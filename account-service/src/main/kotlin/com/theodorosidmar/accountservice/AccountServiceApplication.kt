package com.theodorosidmar.accountservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity

@SpringBootApplication
@EnableDiscoveryClient
@EnableOAuth2Sso
@EnableFeignClients
@EnableGlobalMethodSecurity(prePostEnabled = true)
class AccountServiceApplication

fun main(args: Array<String>) {
	runApplication<AccountServiceApplication>(*args)
}
