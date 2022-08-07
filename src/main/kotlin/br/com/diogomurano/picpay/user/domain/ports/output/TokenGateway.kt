package br.com.diogomurano.picpay.user.domain.ports.output

import br.com.diogomurano.picpay.user.domain.model.User

interface TokenGateway {

    fun generate(user: User): Pair<String, Long>

}
