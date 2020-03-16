package com.theodorosidmar.authservice.dto

import java.io.Serializable

class UserDto : Serializable {
    var id: String? = null
    var username: String? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}
