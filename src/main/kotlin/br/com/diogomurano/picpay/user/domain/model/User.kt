package br.com.diogomurano.picpay.user.domain.model

import br.com.diogomurano.picpay.user.domain.enums.UserType
import org.valiktor.functions.hasSize
import org.valiktor.functions.isEmail
import org.valiktor.functions.isNotBlank
import org.valiktor.functions.matches
import org.valiktor.validate
import java.math.BigDecimal
import java.util.*

data class User(
    val fullName: String,
    val document: String,
    val email: String,
    val password: String,
    val type: UserType,
    val balance: BigDecimal = BigDecimal.ZERO,
    val id: UUID? = null
) {

    init {
        validate(this) {
            validate(User::fullName)
                .isNotBlank()
                .hasSize(min = 5, max = 32)

            validate(User::document)
                .isNotBlank()
                .matches("(^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}\$)|(^\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}\$)"
                    .toRegex())

            validate(User::email)
                .isNotBlank()
                .isEmail()

            validate(User::password)
                .isNotBlank()
        }
    }

}
