package br.com.diogomurano.picpay.transaction.adapter.repository.database

import br.com.diogomurano.picpay.transaction.adapter.repository.entity.TransactionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionEntityDatabase: JpaRepository<TransactionEntity, Long>
