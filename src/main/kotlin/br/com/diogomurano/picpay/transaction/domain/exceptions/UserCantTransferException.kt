package br.com.diogomurano.picpay.transaction.domain.exceptions

import br.com.diogomurano.picpay.common.domain.exceptions.ApplicationException
import org.springframework.http.HttpStatus

class UserCantTransferException(
    message: String = "This user cannot send transfers."
): ApplicationException(
    message, HttpStatus.PRECONDITION_FAILED
)
