package br.com.diogomurano.picpay.common.adapter.controller.security.authentication

import org.springframework.security.authentication.AbstractAuthenticationToken

class ServiceAuthentication(
    private val token: String
) : AbstractAuthenticationToken(null) {

    override fun getCredentials() = null
    override fun getPrincipal() = token
    override fun isAuthenticated() = true

}
