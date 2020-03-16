package com.theodorosidmar.authservice.service

import com.theodorosidmar.authservice.domain.User

interface UserService {
    fun create(user: User): User
}
