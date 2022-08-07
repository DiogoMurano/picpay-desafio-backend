package br.com.diogomurano.picpay.transaction.domain.exceptions

import br.com.diogomurano.picpay.common.domain.exceptions.ApplicationException
import org.springframework.http.HttpStatus

class InsufficientBalanceException(
    message: String = "User does not have enough balance for this transaction"
): ApplicationException(
    message, HttpStatus.PRECONDITION_FAILED
)
