package br.com.diogomurano.picpay.user.domain.model

import br.com.diogomurano.picpay.user.domain.enums.UserType
import java.math.BigDecimal
import java.util.*

object UserMother {

    fun get(
        balance: BigDecimal = BigDecimal.ZERO
    ) = User(
        fullName = "User Name",
        document = "000.000.000-00",
        email = "user@email.com",
        password = "password123",
        balance = balance,
        type = UserType.COMMON,
    )

    fun getWithId(
        id: UUID = UUID.randomUUID(),
        balance: BigDecimal = BigDecimal.ZERO
    ) = User(
        fullName = "User Name",
        document = "000.000.000-00",
        email = "user@email.com",
        password = "password123",
        type = UserType.COMMON,
        balance = balance,
        id = id
    )

}
