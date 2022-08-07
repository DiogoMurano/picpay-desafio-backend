package br.com.diogomurano.picpay.user.domain.exceptions

import br.com.diogomurano.picpay.common.domain.exceptions.ApplicationException
import br.com.diogomurano.picpay.user.domain.enums.UserType
import org.springframework.http.HttpStatus

class UserTypeNotFoundException(
    message: String = "User type informed not found. Values: ${UserType.values().joinToString { "," }}"
) : ApplicationException(message, HttpStatus.BAD_REQUEST)
