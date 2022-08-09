package br.com.diogomurano.picpay.user.usecases

import br.com.diogomurano.picpay.user.domain.exceptions.IncorrectPasswordException
import br.com.diogomurano.picpay.user.domain.exceptions.UserNotFoundException
import br.com.diogomurano.picpay.user.domain.model.User
import br.com.diogomurano.picpay.user.domain.model.UserMother
import br.com.diogomurano.picpay.user.domain.ports.input.LoginUserUseCase
import br.com.diogomurano.picpay.user.domain.ports.output.CryptoGateway
import br.com.diogomurano.picpay.user.domain.ports.output.TokenGateway
import br.com.diogomurano.picpay.user.domain.ports.output.UserRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpStatus
import java.util.*

internal class LoginUserUseCaseImplTest {

    private val userRepository = mockk<UserRepository>()
    private val cryptoGateway = mockk<CryptoGateway>()
    private val tokenGateway = mockk<TokenGateway>()

    private val useCase = LoginUserUseCaseImpl(
        userRepository = userRepository,
        cryptoGateway = cryptoGateway,
        tokenGateway = tokenGateway
    )

    @Test
    fun `should login user successfully`() {
        val user = UserMother.getWithId()

        every { userRepository.findByEmail(user.email) } returns user
        every { cryptoGateway.matches(any(), any()) } returns true

        val expiresIn = 600L
        val accessToken = UUID.randomUUID().toString()

        every { tokenGateway.generate(user) } returns (accessToken to expiresIn)

        val command = buildLoginUserCommand(user)
        val result = assertDoesNotThrow { useCase.execute(command) }

        assertAll(
            { assertEquals(result.userId, user.id) },
            { assertEquals(result.accessToken, accessToken) },
            { assertEquals(result.expiresIn, expiresIn) }
        )
    }

    @Test
    fun `should throw user not found on login user`() {
        val user = UserMother.getWithId()
        val command = buildLoginUserCommand(user)

        every { userRepository.findByEmail(user.email) } returns null

        val throws = assertThrows<UserNotFoundException> { useCase.execute(command) }

        assertAll(
            { assertEquals(throws.reason, "User not found.") },
            { assertEquals(throws.status, HttpStatus.PRECONDITION_FAILED) }
        )
    }

    @Test
    fun `should throw incorrect password on login user`() {
        val user = UserMother.getWithId()
        val command = buildLoginUserCommand(user)

        every { userRepository.findByEmail(user.email) } returns user
        every { cryptoGateway.matches(any(), any()) } returns false

        val throws = assertThrows<IncorrectPasswordException> { useCase.execute(command) }

        assertAll(
            { assertEquals(throws.reason, "Incorrect password.") },
            { assertEquals(throws.status, HttpStatus.PRECONDITION_FAILED) }
        )
    }

    private fun buildLoginUserCommand(user: User) = LoginUserUseCase.LoginUserCommand(
        email = user.email,
        password = user.password
    )

}
