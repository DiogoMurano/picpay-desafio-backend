package br.com.diogomurano.picpay.user.adapter.controller.request

import br.com.diogomurano.picpay.user.domain.ports.input.LoginUserUseCase
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotBlank

data class LoginUserRequest(
    @field:NotBlank(message = "email can't be blank.")
    @field:JsonProperty("email")
    @field:Schema(example = "user@email.com")
    val email: String? = null,

    @field:NotBlank(message = "password can't be blank.")
    @field:JsonProperty("password")
    @field:Schema(example = "password123")
    val password: String? = null
) {

    fun toCommand() = LoginUserUseCase.LoginUserCommand(
        email = email!!,
        password = password!!
    )

}
