package br.com.diogomurano.picpay.user.adapter.repository

import br.com.diogomurano.picpay.user.adapter.repository.database.UserEntityDatabase
import br.com.diogomurano.picpay.user.adapter.repository.mapper.UserEntityMapper
import br.com.diogomurano.picpay.user.domain.exceptions.UserNotFoundException
import br.com.diogomurano.picpay.user.domain.model.User
import br.com.diogomurano.picpay.user.domain.ports.output.UserRepository
import org.springframework.stereotype.Repository
import java.math.BigDecimal
import java.util.*

@Repository
class UserRepositoryImpl(
    private val userEntityDatabase: UserEntityDatabase
) : UserRepository {

    override fun findById(id: UUID) = userEntityDatabase
        .findByExternalId(id)?.let { UserEntityMapper.fromEntityToModel(it) }

    override fun findByEmail(email: String) = userEntityDatabase
        .findByEmail(email)?.let { UserEntityMapper.fromEntityToModel(it) }

    override fun findByDocument(document: String) = userEntityDatabase
        .findByDocument(document)?.let { UserEntityMapper.fromEntityToModel(it) }

    override fun create(user: User) = userEntityDatabase
        .save(UserEntityMapper.fromModelToEntity(user)).let { UserEntityMapper.fromEntityToModel(it) }

    override fun update(user: User): User {
        val findUser = userEntityDatabase.findByExternalId(user.id!!)
            ?: throw UserNotFoundException()
        val updatedUserEntity = UserEntityMapper.fromModelToEntity(user)

        updatedUserEntity.id = findUser.id

        return userEntityDatabase.save(updatedUserEntity).let { UserEntityMapper.fromEntityToModel(it) }
    }

    override fun updateBalanceWithPlusOperation(user: User, value: BigDecimal) =
        userEntityDatabase.updateBalanceWithPlusOperation(user.id!!, value)

    override fun updateBalanceWithMinusOperation(user: User, value: BigDecimal) =
        userEntityDatabase.updateBalanceWithMinusOperation(user.id!!, value)

}
