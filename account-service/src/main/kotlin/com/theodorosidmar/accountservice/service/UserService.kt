package com.theodorosidmar.accountservice.service

import com.theodorosidmar.accountservice.client.AuthServiceClient
import com.theodorosidmar.accountservice.dto.UserDto
import com.theodorosidmar.accountservice.dto.UserRegistrationDto
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val authServiceClient: AuthServiceClient
) {
    fun create(user: UserRegistrationDto): UserDto = authServiceClient.createUser(user)
}
