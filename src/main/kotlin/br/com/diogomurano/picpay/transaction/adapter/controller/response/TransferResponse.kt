package br.com.diogomurano.picpay.transaction.adapter.controller.response

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class TransferResponse(
    @field:JsonProperty("sent_date")
    @field:JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", shape = JsonFormat.Shape.STRING)
    val sentDate: LocalDateTime,

    @field:JsonProperty("message")
    val message: String
)
