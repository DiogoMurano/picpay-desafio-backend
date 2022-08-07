package br.com.diogomurano.picpay.user.domain.ports.input

import java.util.*

interface LoginUserUseCase {

    fun execute(command: LoginUserCommand): LoginUserResult

    data class LoginUserCommand(
        val email: String,
        val password: String
    )

    data class LoginUserResult(
        val userId: UUID,
        val accessToken: String,
        val expiresIn: Long
    )

}
