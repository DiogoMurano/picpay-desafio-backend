package br.com.diogomurano.picpay.user.domain.ports.output

import br.com.diogomurano.picpay.user.domain.model.User
import java.math.BigDecimal
import java.util.*

interface UserRepository {

    fun findById(id: UUID): User?
    fun findByEmail(email: String): User?
    fun findByDocument(document: String): User?

    fun create(user: User): User
    fun update(user: User): User

    fun updateBalanceWithPlusOperation(user: User, value: BigDecimal)
    fun updateBalanceWithMinusOperation(user: User, value: BigDecimal)

}
