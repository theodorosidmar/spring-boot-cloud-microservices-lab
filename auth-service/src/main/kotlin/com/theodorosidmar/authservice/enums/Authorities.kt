package com.theodorosidmar.authservice.enums

import org.springframework.security.core.GrantedAuthority

enum class Authorities: GrantedAuthority {
    ROLE_USER;

    override fun getAuthority(): String = name
}
