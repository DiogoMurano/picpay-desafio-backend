package br.com.diogomurano.picpay.user.usecases

import br.com.diogomurano.picpay.user.domain.exceptions.DocumentAlreadyRegisteredException
import br.com.diogomurano.picpay.user.domain.exceptions.EmailAlreadyRegisteredException
import br.com.diogomurano.picpay.user.domain.model.UserMother
import br.com.diogomurano.picpay.user.domain.ports.output.CryptoGateway
import br.com.diogomurano.picpay.user.domain.ports.output.UserRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpStatus

internal class CreateUserUseCaseImplTest {

    private val repository = mockk<UserRepository>()
    private val cryptoGateway = mockk<CryptoGateway>()

    private val useCase = CreateUserUseCaseImpl(
        repository = repository,
        cryptoGateway = cryptoGateway
    )

    @Test
    fun `should create user successfully`() {
        val user = UserMother.get()

        every { repository.findByEmail(user.email) } returns null
        every { repository.findByDocument(user.document) } returns null
        every { cryptoGateway.encrypt(user.password) } returns user.password

        every { repository.create(user) } returns user

        val createdUser = assertDoesNotThrow { useCase.execute(user) }

        assertEquals(user, createdUser)
    }

    @Test
    fun `should throw email already registered on create user`() {
        val user = UserMother.get()

        every { repository.findByEmail(user.email) } returns user

        val throws = assertThrows<EmailAlreadyRegisteredException> {
            useCase.execute(user)
        }

        assertAll(
            { assertEquals(throws.reason, "Email already registered to another user.") },
            { assertEquals(throws.status, HttpStatus.CONFLICT) }
        )
    }

    @Test
    fun `should throw document already registered on create user`() {
        val user = UserMother.get()

        every { repository.findByEmail(user.email) } returns null
        every { repository.findByDocument(user.document) } returns user

        val throws = assertThrows<DocumentAlreadyRegisteredException> {
            useCase.execute(user)
        }

        assertAll(
            { assertEquals(throws.reason, "Document already registered to another user.") },
            { assertEquals(throws.status, HttpStatus.CONFLICT) }
        )
    }

}
