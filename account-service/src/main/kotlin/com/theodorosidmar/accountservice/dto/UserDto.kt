package com.theodorosidmar.accountservice.dto

import java.io.Serializable

data class UserDto(
    val id: String? = null,
    val username: String? = null
) : Serializable {
    companion object {
        private const val serialVersionUID = 1L
    }
}
