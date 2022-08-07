package br.com.diogomurano.picpay.transaction.usecases

import br.com.diogomurano.picpay.transaction.domain.exceptions.InsufficientBalanceException
import br.com.diogomurano.picpay.transaction.domain.exceptions.TransferNotAllowedException
import br.com.diogomurano.picpay.transaction.domain.exceptions.UserCantTransferException
import br.com.diogomurano.picpay.transaction.domain.model.Transaction
import br.com.diogomurano.picpay.transaction.domain.ports.input.TransferUseCase
import br.com.diogomurano.picpay.transaction.domain.ports.input.TransferUseCase.TransferCommand
import br.com.diogomurano.picpay.transaction.domain.ports.input.TransferUseCase.TransferResult
import br.com.diogomurano.picpay.transaction.domain.ports.output.TransactionRepository
import br.com.diogomurano.picpay.transaction.domain.ports.output.TransferValidationClient
import br.com.diogomurano.picpay.transaction.domain.ports.output.TransferValidationClient.TransferValidationResult
import br.com.diogomurano.picpay.user.domain.enums.UserType
import br.com.diogomurano.picpay.user.domain.exceptions.UserNotFoundException
import br.com.diogomurano.picpay.user.domain.ports.output.NotifyUserGateway
import br.com.diogomurano.picpay.user.domain.ports.output.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.transaction.Transactional

@Service
class TransferUseCaseImpl(
    private val userRepository: UserRepository,
    private val transactionRepository: TransactionRepository,
    private val transferValidationClient: TransferValidationClient,
    private val notifyUserGateway: NotifyUserGateway
) : TransferUseCase {

    @Transactional
    override fun execute(command: TransferCommand): TransferResult {
        val transactionValue = command.value
        val fromUser = userRepository.findById(command.fromUserId)
            ?: throw UserNotFoundException()

        if (fromUser.type == UserType.RETAILER) {
            throw UserCantTransferException()
        }

        if (fromUser.balance < transactionValue) {
            throw InsufficientBalanceException()
        }

        val targetUser = userRepository.findById(command.targetUserId)
            ?: throw UserNotFoundException("Target not found.")

        val transaction = Transaction(
            from = fromUser,
            target = targetUser,
            value = transactionValue,
            description = command.description
        )

        if (transferValidationClient.validate(transaction) == TransferValidationResult.DENIED) {
            throw TransferNotAllowedException()
        }

        userRepository.updateBalanceWithMinusOperation(fromUser, transactionValue)
        userRepository.updateBalanceWithPlusOperation(targetUser, transactionValue)
        transactionRepository.storage(transaction)

        notifyUserGateway.notify(
            user = targetUser,
            messageTitle = "Transferência recebida com sucesso",
            messageBody = """
                Você recebeu uma transferência de R${'$'} ${command.value.setScale(2)} enviada por ${fromUser.fullName}
            """.trimIndent()
        )

        return TransferResult(
            sentDate = LocalDateTime.now()
        )
    }

}
