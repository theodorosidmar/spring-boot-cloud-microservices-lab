package com.theodorosidmar.gatewayservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.zuul.EnableZuulProxy

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
class GatewayServiceApplication

fun main(args: Array<String>) {
	runApplication<GatewayServiceApplication>(*args)
}
