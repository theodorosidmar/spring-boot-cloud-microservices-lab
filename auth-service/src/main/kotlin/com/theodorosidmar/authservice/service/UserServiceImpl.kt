package com.theodorosidmar.authservice.service

import com.theodorosidmar.authservice.domain.User
import com.theodorosidmar.authservice.enums.Authorities
import com.theodorosidmar.authservice.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository
): UserService {
    override fun create(user: User): User {
        userRepository.findByUsername(user.username!!) ?:
                throw IllegalArgumentException("User not available")
        val hash = passwordEncoder.encode(user.password)
        user.password = hash
        user.setActivated(true) // TODO send sms or email with code for activation
        user.setAuthorities(listOf(Authorities.ROLE_USER).toSet())

        // TODO other routines on account creation

        return userRepository.save(user)
    }
}
