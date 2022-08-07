package br.com.diogomurano.picpay.transaction.domain.ports.output

import br.com.diogomurano.picpay.transaction.domain.model.Transaction

interface TransactionRepository {

    fun storage(transaction: Transaction)

}
