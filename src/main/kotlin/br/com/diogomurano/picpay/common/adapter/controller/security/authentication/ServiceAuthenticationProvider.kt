package br.com.diogomurano.picpay.common.adapter.controller.security.authentication

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class ServiceAuthenticationProvider : AuthenticationProvider {

    override fun authenticate(authentication: Authentication) = authentication

    override fun supports(authentication: Class<*>) =
        ServiceAuthentication::class.java.isAssignableFrom(authentication)

}
