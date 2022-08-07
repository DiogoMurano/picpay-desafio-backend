package br.com.diogomurano.picpay.transaction.domain.ports.output

import br.com.diogomurano.picpay.transaction.domain.model.Transaction

interface TransferValidationClient {

    fun validate(transaction: Transaction): TransferValidationResult

    enum class TransferValidationResult {
        ALLOWED,
        DENIED
    }

}
