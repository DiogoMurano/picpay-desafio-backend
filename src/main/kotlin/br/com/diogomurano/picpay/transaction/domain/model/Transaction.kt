package br.com.diogomurano.picpay.transaction.domain.model

import br.com.diogomurano.picpay.user.domain.model.User
import org.valiktor.functions.isNotBlank
import org.valiktor.functions.isPositive
import org.valiktor.validate
import java.math.BigDecimal

data class Transaction(
    val from: User,
    val target: User,
    val value: BigDecimal,
    val description: String
) {

    init {
        validate(this) {
            validate(Transaction::value)
                .isPositive()
            validate(Transaction::description)
                .isNotBlank()
        }
    }

}
