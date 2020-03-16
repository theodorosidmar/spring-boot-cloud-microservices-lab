package com.theodorosidmar.authservice.configuration.mongo.changelogs

import com.github.mongobee.changeset.ChangeLog
import com.github.mongobee.changeset.ChangeSet
import com.theodorosidmar.authservice.domain.AuthClientDetails
import com.theodorosidmar.authservice.enums.Authorities
import com.theodorosidmar.authservice.domain.User
import org.springframework.data.mongodb.core.MongoTemplate

@ChangeLog
class InitialValuesChangeLog {
    @ChangeSet(order = "001", id = "insertBrowserClientDetails", author = "Sidmar Theodoro")
    fun insertBrowserClientDetails(mongoTemplate: MongoTemplate) {
        AuthClientDetails(
            clientId = "browser",
            clientSecret = "$2a$10\$fWNTd3H.u7G/aNROVQSifebOkZ2xzU5nUPOCI2Ld42M8E25/ljJqK",
            scopes = "ui",
            grantTypes = "refresh_token,password"
        ).let {
            mongoTemplate.save(it)
        }
    }

    @ChangeSet(order = "002", id = "insertUserToTestAuthentication", author = "Sidmar Theodoro")
    fun insertUserToTestAuthentication(mongoTemplate: MongoTemplate) {
        val authorities: MutableSet<Authorities> = HashSet()
        authorities.add(Authorities.ROLE_USER)
        User(
            authorities = authorities,
            password = "$2a$10\$fWNTd3H.u7G/aNROVQSifebOkZ2xzU5nUPOCI2Ld42M8E25/ljJqK",
            username = "randomuser"
        ).let {
            mongoTemplate.save<Any>(it)
        }
    }

    @ChangeSet(order = "003", id = "insertAccountServiceClientDetails", author = "Sidmar Theodoro")
    fun insertAccountServiceClientDetails(mongoTemplate: MongoTemplate) {
        AuthClientDetails(
                clientId = "account-service",
                clientSecret = "$2a$10\$fWNTd3H.u7G/aNROVQSifebOkZ2xzU5nUPOCI2Ld42M8E25/ljJqK",
                scopes = "server",
                grantTypes = "refresh_token,client_credentials"
        ).let {
            mongoTemplate.save(it)
        }
    }
}
