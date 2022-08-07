package br.com.diogomurano.picpay.transaction.domain.exceptions

import br.com.diogomurano.picpay.common.domain.exceptions.ApplicationException
import org.springframework.http.HttpStatus

class TransferNotAllowedException(
    message: String = "This transfer was not authorized"
): ApplicationException(message, HttpStatus.UNPROCESSABLE_ENTITY)
