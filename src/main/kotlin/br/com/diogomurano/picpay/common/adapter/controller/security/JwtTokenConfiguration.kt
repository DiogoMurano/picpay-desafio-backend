package br.com.diogomurano.picpay.common.adapter.controller.security

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JwtTokenConfiguration(
    @Value("\${security.jwt.secret}")
    private val jwtSecret: String
) {

    @Bean
    fun tokenAlgorithm(): Algorithm =
        Algorithm.HMAC512(jwtSecret)

    @Bean
    fun verifier(): JWTVerifier = JWT
        .require(tokenAlgorithm())
        .withIssuer(ISSUER)
        .build()

    companion object {
        const val ISSUER = "PICPAY-CHALLENGE"
    }

}
