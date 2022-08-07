package br.com.diogomurano.picpay.user.usecases

import br.com.diogomurano.picpay.user.domain.exceptions.IncorrectPasswordException
import br.com.diogomurano.picpay.user.domain.exceptions.UserNotFoundException
import br.com.diogomurano.picpay.user.domain.ports.input.LoginUserUseCase
import br.com.diogomurano.picpay.user.domain.ports.input.LoginUserUseCase.LoginUserCommand
import br.com.diogomurano.picpay.user.domain.ports.input.LoginUserUseCase.LoginUserResult
import br.com.diogomurano.picpay.user.domain.ports.output.CryptoGateway
import br.com.diogomurano.picpay.user.domain.ports.output.TokenGateway
import br.com.diogomurano.picpay.user.domain.ports.output.UserRepository
import org.springframework.stereotype.Service

@Service
class LoginUserUseCaseImpl(
    private val userRepository: UserRepository,
    private val cryptoGateway: CryptoGateway,
    private val tokenGateway: TokenGateway
) : LoginUserUseCase {

    override fun execute(command: LoginUserCommand): LoginUserResult {
        val user = userRepository.findByEmail(command.email)
            ?: throw UserNotFoundException()

        if (!cryptoGateway.matches(command.password, user.password)) {
            throw IncorrectPasswordException()
        }

        val generatedToken = tokenGateway.generate(user)

        return LoginUserResult(
            userId = user.id!!,
            accessToken = generatedToken.first,
            expiresIn = generatedToken.second
        )
    }

}
