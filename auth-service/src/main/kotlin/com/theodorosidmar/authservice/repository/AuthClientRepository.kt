package com.theodorosidmar.authservice.repository

import com.theodorosidmar.authservice.domain.AuthClientDetails
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthClientRepository: MongoRepository<AuthClientDetails, String> {
    fun findByClientId(clientId: String): AuthClientDetails?
}
