package br.com.diogomurano.picpay.user.adapter.crypto

import br.com.diogomurano.picpay.user.domain.ports.output.CryptoGateway
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class BCryptGatewayImpl : CryptoGateway {

    private val delegate = BCryptPasswordEncoder()

    override fun encrypt(decryptedString: String): String = delegate.encode(decryptedString)

    override fun matches(decryptedString: String, encryptedString: String): Boolean =
        delegate.matches(decryptedString, encryptedString)

}
