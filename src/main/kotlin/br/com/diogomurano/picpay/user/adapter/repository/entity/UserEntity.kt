package br.com.diogomurano.picpay.user.adapter.repository.entity

import br.com.diogomurano.picpay.common.adapter.repository.AbstractJpaPersistable
import br.com.diogomurano.picpay.user.domain.enums.UserType
import org.hibernate.annotations.Type
import java.math.BigDecimal
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "users")
class UserEntity(
    @field:Column(name = "full_name")
    val fullName: String,
    @field:Column(name = "document", unique = true)
    val document: String,
    @field:Column(name = "email", unique = true)
    val email: String,
    @field:Column(name = "password")
    val password: String,
    @field:Column(name = "type")
    @field:Enumerated(EnumType.STRING)
    @field:Type(type = "br.com.diogomurano.picpay.common.adapter.repository.configuration.PostgreSQLEnumType")
    val type: UserType,
    @field:Column(name = "balance")
    val balance: BigDecimal = BigDecimal.ZERO,
    @field:Column(name = "external_id", unique = true, updatable = false, nullable = false)
    val externalId: UUID
): AbstractJpaPersistable<Long>()
