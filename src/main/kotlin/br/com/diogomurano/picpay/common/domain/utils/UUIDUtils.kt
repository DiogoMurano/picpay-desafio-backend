package br.com.diogomurano.picpay.common.domain.utils

import br.com.diogomurano.picpay.common.domain.exceptions.ApplicationException
import org.springframework.http.HttpStatus
import java.util.*

fun uuidCustomValueOf(value: String, fieldName: String): UUID = try {
    UUID.fromString(value)
} catch (e: Exception) {
    throw ApplicationException("$fieldName must be a uuid.", HttpStatus.BAD_REQUEST)
}
