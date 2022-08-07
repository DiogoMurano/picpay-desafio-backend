package br.com.diogomurano.picpay.transaction.adapter.client

import com.fasterxml.jackson.annotation.JsonProperty

data class NotifyMessage(
    @field:JsonProperty("email")
    val email: String,
    @field:JsonProperty("message_title")
    val messageTitle: String,
    @field:JsonProperty("message_body")
    val messageBody: String
)
