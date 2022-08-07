package br.com.diogomurano.picpay.user.usecases

import br.com.diogomurano.picpay.user.domain.exceptions.DocumentAlreadyRegisteredException
import br.com.diogomurano.picpay.user.domain.exceptions.EmailAlreadyRegisteredException
import br.com.diogomurano.picpay.user.domain.model.User
import br.com.diogomurano.picpay.user.domain.ports.input.CreateUserUseCase
import br.com.diogomurano.picpay.user.domain.ports.output.CryptoGateway
import br.com.diogomurano.picpay.user.domain.ports.output.UserRepository
import org.springframework.stereotype.Service

@Service
class CreateUserUseCaseImpl(
    private val repository: UserRepository,
    private val cryptoGateway: CryptoGateway
) : CreateUserUseCase {

    override fun execute(user: User): User {
        if (repository.findByEmail(user.email) != null) {
            throw EmailAlreadyRegisteredException()
        }

        if (repository.findByDocument(user.document) != null) {
            throw DocumentAlreadyRegisteredException()
        }

        val userWithEncryptedPassword = user.copy(
            password = cryptoGateway.encrypt(user.password)
        )

        return repository.create(userWithEncryptedPassword)
    }

}
