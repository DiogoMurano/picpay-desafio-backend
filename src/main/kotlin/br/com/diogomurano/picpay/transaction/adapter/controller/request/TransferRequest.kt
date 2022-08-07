package br.com.diogomurano.picpay.transaction.adapter.controller.request

import br.com.diogomurano.picpay.common.domain.utils.uuidCustomValueOf
import br.com.diogomurano.picpay.transaction.domain.ports.input.TransferUseCase
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive

data class TransferRequest(
    @field:NotBlank(message = "target_id can't be blank.")
    @field:JsonProperty("target_id")
    val targetId: String,

    @field:Positive(message = "value must be positive.")
    @field:JsonProperty("value")
    val value: BigDecimal,

    @field:NotBlank(message = "description can't be blank.")
    @field:JsonProperty("description")
    @field:Schema(example = "message")
    val description: String
) {

    fun toCommand(fromUserId: String) = TransferUseCase.TransferCommand(
        fromUserId = uuidCustomValueOf(fromUserId, "user_id"),
        targetUserId = uuidCustomValueOf(targetId, "target_id"),
        value = value,
        description = description
    )

}
