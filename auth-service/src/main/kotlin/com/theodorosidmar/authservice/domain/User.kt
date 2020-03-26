package com.theodorosidmar.authservice.domain

import com.theodorosidmar.authservice.enums.Authorities
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Document
class User(
    private var activated: Boolean = false,
    private var authorities: Set<Authorities> = HashSet(),
    @Indexed(unique = true)
    private val username: String,
    private var password: String
): UserDetails {
    @Id
    var id: String? = null

    private val activationKey: String? = null

    private val resetPasswordKey: String? = null

    fun setPassword(password: String) { this.password = password }

    fun setActivated(activated: Boolean) { this.activated = activated }

    fun setAuthorities(authorities: Set<Authorities>) { this.authorities = authorities }

    override fun getAuthorities(): List<GrantedAuthority> = ArrayList<GrantedAuthority>(authorities)

    override fun isEnabled(): Boolean = activated

    override fun getUsername(): String? = username

    override fun isCredentialsNonExpired(): Boolean = true

    override fun getPassword(): String = password

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true
}
