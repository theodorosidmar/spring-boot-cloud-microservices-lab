package com.theodorosidmar.authservice.service

import com.theodorosidmar.authservice.repository.AuthClientRepository
import org.springframework.security.oauth2.provider.ClientDetails
import org.springframework.security.oauth2.provider.ClientDetailsService
import org.springframework.stereotype.Service

@Service
class AuthClientDetailsService(
    private val authClientRepository: AuthClientRepository
): ClientDetailsService {
    override fun loadClientByClientId(clientId: String): ClientDetails =
        authClientRepository.findByClientId(clientId) ?:
            throw IllegalArgumentException()
}
