package br.com.diogomurano.picpay.user.adapter.controller

import br.com.diogomurano.picpay.user.adapter.controller.request.CreateUserRequest
import br.com.diogomurano.picpay.user.domain.ports.input.CreateUserUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User Endpoints")
class UserController(
    private val createUserUseCase: CreateUserUseCase
) {

    @PostMapping
    @Operation(
        summary = "Create user",
        description = "On success returns 201 status code.",
    )
    @ApiResponses(
        value = [ApiResponse(responseCode = "201", description = "User created successfully.")]
    )
    fun createUser(
        @Valid @RequestBody request: CreateUserRequest
    ): ResponseEntity<Any> {
        val createdUser = createUserUseCase.execute(request.toModel())
        return ResponseEntity
            .created(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdUser.id!!.toString())
                .toUri())
            .build()
    }

}
