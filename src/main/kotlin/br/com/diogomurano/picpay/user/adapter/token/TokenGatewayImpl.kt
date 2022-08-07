package br.com.diogomurano.picpay.user.adapter.token

import br.com.diogomurano.picpay.common.adapter.controller.security.JwtTokenConfiguration.Companion.ISSUER
import br.com.diogomurano.picpay.user.domain.model.User
import br.com.diogomurano.picpay.user.domain.ports.output.TokenGateway
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class TokenGatewayImpl(
    private val algorithm: Algorithm,
    @Value("\${security.jwt.expires-after}")
    private val jwtExpiresIn: String
): TokenGateway {

    override fun generate(user: User): Pair<String, Long> {
        return JWT.create()
            .withExpiresAt(Date(System.currentTimeMillis() + (jwtExpiresIn.toLong() * 1000)))
            .withSubject(user.id!!.toString())
            .withIssuer(ISSUER)
            .sign(algorithm) to jwtExpiresIn.toLong()
    }

}
