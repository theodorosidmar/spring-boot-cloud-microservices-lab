package com.theodorosidmar.authservice.configuration.mongo

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "spring.data.mongodb")
class MongoProperties {
    var host: String? = null
    var port = 0
    var username: String? = null
    var password: String? = null
    var database: String? = null
}
