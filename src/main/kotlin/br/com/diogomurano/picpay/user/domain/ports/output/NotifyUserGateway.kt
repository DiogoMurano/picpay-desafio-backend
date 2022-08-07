package br.com.diogomurano.picpay.user.domain.ports.output

import br.com.diogomurano.picpay.user.domain.model.User

interface NotifyUserGateway {

    fun notify(
        user: User,
        messageTitle: String,
        messageBody: String
    )

}
