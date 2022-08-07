package br.com.diogomurano.picpay.transaction.adapter.repository

import br.com.diogomurano.picpay.transaction.adapter.repository.database.TransactionEntityDatabase
import br.com.diogomurano.picpay.transaction.adapter.repository.entity.TransactionEntity
import br.com.diogomurano.picpay.transaction.domain.model.Transaction
import br.com.diogomurano.picpay.transaction.domain.ports.output.TransactionRepository
import br.com.diogomurano.picpay.user.adapter.repository.database.UserEntityDatabase
import br.com.diogomurano.picpay.user.domain.exceptions.UserNotFoundException
import org.springframework.stereotype.Repository

@Repository
class TransactionRepositoryImpl(
    private val transactionEntityDatabase: TransactionEntityDatabase,
    private val userEntityDatabase: UserEntityDatabase
) : TransactionRepository {

    override fun storage(transaction: Transaction) {
        val fromUserEntity = userEntityDatabase.findByExternalId(transaction.from.id!!)
            ?: throw UserNotFoundException()
        val targetUserEntity = userEntityDatabase.findByExternalId(transaction.target.id!!)
            ?: throw UserNotFoundException()

        transactionEntityDatabase.save(
            TransactionEntity(
                fromUser = fromUserEntity,
                targetUser = targetUserEntity,
                value = transaction.value,
                description = transaction.description
            )
        )
    }

}
