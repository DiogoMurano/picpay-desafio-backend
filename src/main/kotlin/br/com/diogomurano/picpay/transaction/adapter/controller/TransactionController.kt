package br.com.diogomurano.picpay.transaction.adapter.controller

import br.com.diogomurano.picpay.transaction.adapter.controller.request.TransferRequest
import br.com.diogomurano.picpay.transaction.adapter.controller.response.TransferResponse
import br.com.diogomurano.picpay.transaction.domain.ports.input.TransferUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/users/{user_id}")
@Tag(name = "Transaction Endpoints")
@SecurityRequirement(name = "Bearer Token")
class TransactionController(
    private val transferUseCase: TransferUseCase
) {

    @PostMapping("/transfer")
    @Operation(
        summary = "Send money transfer",
        description = "On success returns 200 status code.",
    )
    @ApiResponses(
        value = [ApiResponse(responseCode = "200", description = "Transfer sent successfully.")]
    )
    fun transfer(
        @PathVariable("user_id") userId: String,
        @Valid @RequestBody request: TransferRequest
    ): ResponseEntity<TransferResponse> {
        val result = transferUseCase.execute(request.toCommand(userId))
        return ResponseEntity
            .ok(TransferResponse(
                sentDate = result.sentDate,
                message = "Transfer sent successfully."
            ))
    }

}
