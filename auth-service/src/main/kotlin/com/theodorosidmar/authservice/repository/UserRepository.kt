package com.theodorosidmar.authservice.repository

import com.theodorosidmar.authservice.domain.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: MongoRepository<User, String> {
    fun findByUsername(username: String): User?
}
