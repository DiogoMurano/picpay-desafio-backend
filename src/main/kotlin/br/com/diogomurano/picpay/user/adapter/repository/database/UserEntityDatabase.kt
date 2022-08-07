package br.com.diogomurano.picpay.user.adapter.repository.database

import br.com.diogomurano.picpay.user.adapter.repository.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.util.*

@Repository
interface UserEntityDatabase: JpaRepository<UserEntity, Long> {

    fun findByEmail(email: String): UserEntity?
    fun findByDocument(document: String): UserEntity?
    fun findByExternalId(externalId: UUID): UserEntity?

    @Modifying
    @Query("UPDATE UserEntity u SET u.balance = u.balance + :value WHERE u.externalId = :externalId")
    fun updateBalanceWithPlusOperation(externalId: UUID, value: BigDecimal)

    @Modifying
    @Query("UPDATE UserEntity u SET u.balance = u.balance - :value WHERE u.externalId = :externalId")
    fun updateBalanceWithMinusOperation(externalId: UUID, value: BigDecimal)

}
