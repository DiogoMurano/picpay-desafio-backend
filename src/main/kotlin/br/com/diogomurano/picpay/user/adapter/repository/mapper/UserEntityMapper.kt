package br.com.diogomurano.picpay.user.adapter.repository.mapper

import br.com.diogomurano.picpay.user.adapter.repository.entity.UserEntity
import br.com.diogomurano.picpay.user.domain.model.User
import java.util.*

object UserEntityMapper {

    fun fromModelToEntity(from: User) = UserEntity(
        fullName = from.fullName,
        document = from.document,
        email = from.email,
        type = from.type,
        password = from.password,
        balance = from.balance,
        externalId = from.id ?: UUID.randomUUID()
    )

    fun fromEntityToModel(from: UserEntity) = User(
        fullName = from.fullName,
        document = from.document,
        email = from.email,
        type = from.type,
        password = from.password,
        balance = from.balance,
        id = from.externalId
    )

}
