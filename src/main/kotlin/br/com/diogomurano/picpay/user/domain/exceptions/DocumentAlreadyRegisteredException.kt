package br.com.diogomurano.picpay.user.domain.exceptions

import br.com.diogomurano.picpay.common.domain.exceptions.ApplicationException
import org.springframework.http.HttpStatus

class DocumentAlreadyRegisteredException(
    message: String = "Document already registered to another user."
): ApplicationException(message, HttpStatus.CONFLICT)
