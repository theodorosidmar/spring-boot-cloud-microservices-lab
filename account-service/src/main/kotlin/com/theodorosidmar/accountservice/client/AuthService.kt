package com.theodorosidmar.accountservice.client

import com.theodorosidmar.accountservice.dto.UserDto
import com.theodorosidmar.accountservice.dto.UserRegistrationDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "auth-service")
interface AuthServiceClient {
    @PostMapping("/uaa/user")
    fun createUser(@RequestBody userRegistration: UserRegistrationDto): UserDto
}
