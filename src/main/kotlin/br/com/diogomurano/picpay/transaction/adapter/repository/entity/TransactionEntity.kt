package br.com.diogomurano.picpay.transaction.adapter.repository.entity

import br.com.diogomurano.picpay.common.adapter.repository.AbstractJpaPersistable
import br.com.diogomurano.picpay.user.adapter.repository.entity.UserEntity
import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "transactions")
class TransactionEntity(
    @field:ManyToOne
    @field:JoinColumn(name="from_user_id", nullable=false)
    val fromUser: UserEntity,
    @field:ManyToOne
    @field:JoinColumn(name="target_user_id", nullable=false)
    val targetUser: UserEntity,
    @field:Column
    val value: BigDecimal,
    @field:Column
    val description: String
): AbstractJpaPersistable<Long>()
