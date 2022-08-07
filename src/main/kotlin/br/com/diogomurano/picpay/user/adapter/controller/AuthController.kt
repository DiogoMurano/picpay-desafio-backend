package br.com.diogomurano.picpay.user.adapter.controller

import br.com.diogomurano.picpay.user.adapter.controller.request.LoginUserRequest
import br.com.diogomurano.picpay.user.adapter.controller.response.LoginUserResponse
import br.com.diogomurano.picpay.user.domain.ports.input.LoginUserUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth Endpoints")
class AuthController(
    private val loginUserUseCase: LoginUserUseCase
) {

    @PostMapping("/login")
    @Operation(
        summary = "Login user",
        description = "On success returns OK status code with access token.",
    )
    @ApiResponses(
        value = [ApiResponse(responseCode = "200", description = "Auth successfully.")]
    )
    fun loginUser(
        @Valid @RequestBody request: LoginUserRequest
    ): ResponseEntity<LoginUserResponse> {
        val result = loginUserUseCase.execute(request.toCommand())
        return ResponseEntity.ok(LoginUserResponse(result))
    }

}
