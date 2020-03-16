package com.theodorosidmar.authservice.controller

import com.theodorosidmar.authservice.domain.User
import com.theodorosidmar.authservice.dto.UserDto
import com.theodorosidmar.authservice.dto.UserRegistrationDto
import com.theodorosidmar.authservice.service.UserService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.security.Principal
import javax.validation.Valid

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService
) {
    @GetMapping("/current")
    fun getUser(principal: Principal): Principal = principal

    @PostMapping
    @PreAuthorize("#oauth2.hasScope('server')")
    fun createUser(@RequestBody userRegistration: @Valid UserRegistrationDto?): UserDto? {
        val savedUser = userService.create(toUser(userRegistration!!)!!)
        return toDto(savedUser)
    }

    private fun toDto(user: User): UserDto? =
        UserDto().apply {
            id = user.id
            username = user.username
        }

    private fun toUser(userRegistration: UserRegistrationDto): User? = User(
        username = userRegistration.username!!,
        password = userRegistration.password!!
    )
}
