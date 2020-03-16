package com.theodorosidmar.authservice.configuration.mongo

import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@Configuration
@EnableMongoRepositories(basePackages = ["com.theodorosidmar.authservice.repository"])
class MongoConfiguration {
}
