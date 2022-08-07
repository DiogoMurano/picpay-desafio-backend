package br.com.diogomurano.picpay.common.domain.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

open class ApplicationException(
    message: String,
    status: HttpStatus,
    cause: Throwable? = null
): ResponseStatusException(status, message, cause)
