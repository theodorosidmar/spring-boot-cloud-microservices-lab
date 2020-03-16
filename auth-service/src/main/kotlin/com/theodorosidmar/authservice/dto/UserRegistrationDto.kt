package com.theodorosidmar.authservice.dto

import java.io.Serializable
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class UserRegistrationDto : Serializable {
    @NotNull
    @NotBlank
    var username: String? = null
    @NotNull
    @NotBlank
    var password: String? = null

    companion object {
        private const val serialVersionUID = 1L
    }
}
