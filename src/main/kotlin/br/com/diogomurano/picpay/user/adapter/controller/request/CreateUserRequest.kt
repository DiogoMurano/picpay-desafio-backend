package br.com.diogomurano.picpay.user.adapter.controller.request

import br.com.diogomurano.picpay.user.domain.enums.UserType
import br.com.diogomurano.picpay.user.domain.exceptions.UserTypeNotFoundException
import br.com.diogomurano.picpay.user.domain.model.User
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import javax.validation.constraints.NotBlank

data class CreateUserRequest(
    @field:JsonProperty("full_name")
    @field:NotBlank(message = "full_name can't be blank.")
    @field:Schema(example = "User Name")
    val fullName: String? = null,

    @field:JsonProperty("document")
    @field:NotBlank(message = "document can't be blank.")
    @field:Schema(example = "000.000.000-00")
    val document: String? = null,

    @field:JsonProperty("email")
    @field:NotBlank(message = "email can't be blank.")
    @field:Schema(example = "user@email.com")
    val email: String? = null,

    @field:JsonProperty("password")
    @field:NotBlank(message = "password can't be blank.")
    @field:Schema(example = "password123")
    val password: String? = null,

    @field:JsonProperty("type")
    @field:NotBlank(message = "type can't be blank.")
    @field:Schema(example = "COMMON or RETAILER")
    val type: String? = null
) {

    fun toModel() = User(
        fullName = fullName!!,
        document = document!!,
        email = email!!,
        password = password!!,
        type = UserType.findByName(type!!) ?: throw UserTypeNotFoundException()
    )

}
