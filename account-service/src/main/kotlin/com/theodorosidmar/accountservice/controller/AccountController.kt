package com.theodorosidmar.accountservice.controller

import com.theodorosidmar.accountservice.dto.UserDto
import com.theodorosidmar.accountservice.dto.UserRegistrationDto
import com.theodorosidmar.accountservice.service.AccountService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AccountController(
    private val accountService: AccountService
) {
    @PostMapping
    fun createNewAccount(@RequestBody user: UserRegistrationDto): UserDto = accountService.create(user)
}
