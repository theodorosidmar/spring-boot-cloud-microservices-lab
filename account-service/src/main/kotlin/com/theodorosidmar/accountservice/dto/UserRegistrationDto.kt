package com.theodorosidmar.accountservice.dto

import java.io.Serializable

class UserRegistrationDto(
    val username: String? = null,
    val password: String? = null
) : Serializable {
    companion object {
        private const val serialVersionUID = 1L
    }
}
