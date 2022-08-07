package br.com.diogomurano.picpay.transaction.domain.ports.input

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

interface TransferUseCase {

    fun execute(command: TransferCommand): TransferResult

    data class TransferCommand(
        val fromUserId: UUID,
        val targetUserId: UUID,
        val value: BigDecimal,
        val description: String
    )

    data class TransferResult(
        val sentDate: LocalDateTime
    )

}
