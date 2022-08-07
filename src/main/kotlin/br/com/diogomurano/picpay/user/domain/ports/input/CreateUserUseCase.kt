package br.com.diogomurano.picpay.user.domain.ports.input

import br.com.diogomurano.picpay.user.domain.model.User

interface CreateUserUseCase {

    fun execute(user: User): User

}
