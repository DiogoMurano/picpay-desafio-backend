package br.com.diogomurano.picpay.user.adapter.controller.response

import br.com.diogomurano.picpay.user.domain.ports.input.LoginUserUseCase
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

data class LoginUserResponse(
    @field:JsonProperty("user_id")
    val userId: UUID,
    @field:JsonProperty("access_token")
    val accessToken: String,
    @field:JsonProperty("expires_in")
    val expiresIn: Long
) {

    constructor(result: LoginUserUseCase.LoginUserResult) : this(
        userId = result.userId,
        accessToken = result.accessToken,
        expiresIn = result.expiresIn
    )

}
