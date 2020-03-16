package com.theodorosidmar.authservice.configuration.mongo

import com.github.mongobee.Mongobee
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.data.mongodb.core.MongoTemplate

@Configuration
@DependsOn("mongoTemplate")
class MongobeeConfiguration(
    private val mongoProperties: MongoProperties,
    private val mongoTemplate: MongoTemplate
) {
    private val MONGODB_URL_FORMAT = "mongodb://%s:%s@%s:%d/%s"
    private val MONGODB_CHANGELOGS_PACKAGE = "com.theodorosidmar.authservice.configuration.mongo.changelogs"

    @Bean
    fun mongobee(): Mongobee =
        Mongobee(String.format(MONGODB_URL_FORMAT,
                mongoProperties.username,
                mongoProperties.password,
                mongoProperties.host,
                mongoProperties.port,
                mongoProperties.database)
        ).apply {
            setMongoTemplate(mongoTemplate)
            setDbName(mongoProperties.database)
            setChangeLogsScanPackage(MONGODB_CHANGELOGS_PACKAGE)
        }
}
