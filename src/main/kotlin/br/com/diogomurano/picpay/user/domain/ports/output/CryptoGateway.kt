package br.com.diogomurano.picpay.user.domain.ports.output

interface CryptoGateway {

    fun encrypt(decryptedString: String): String

    fun matches(decryptedString: String, encryptedString: String): Boolean

}
