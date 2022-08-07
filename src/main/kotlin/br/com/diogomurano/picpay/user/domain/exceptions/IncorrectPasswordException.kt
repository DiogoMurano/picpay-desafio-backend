package br.com.diogomurano.picpay.user.domain.exceptions

import br.com.diogomurano.picpay.common.domain.exceptions.ApplicationException
import org.springframework.http.HttpStatus

class IncorrectPasswordException(
    message: String = "Incorrect password."
): ApplicationException(message, HttpStatus.PRECONDITION_FAILED)
