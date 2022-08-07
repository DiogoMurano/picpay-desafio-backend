package br.com.diogomurano.picpay.transaction.adapter.client

import br.com.diogomurano.picpay.common.adapter.client.request
import br.com.diogomurano.picpay.transaction.domain.model.Transaction
import br.com.diogomurano.picpay.transaction.domain.ports.output.TransferValidationClient
import br.com.diogomurano.picpay.transaction.domain.ports.output.TransferValidationClient.TransferValidationResult
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

@Component
class TransferValidationClientImpl(
    private val restTemplate: RestTemplate,
    @Value("\${client.transfer.validator-url}")
    private val validateTransferEndpoint: String
) : TransferValidationClient {

    override fun validate(transaction: Transaction): TransferValidationResult {
        return try {
            val response = restTemplate.request(
                endpoint = validateTransferEndpoint,
                httpMethod = HttpMethod.GET,
                requestBody = transaction,
                headers = emptyMap()
            )

            when (response.statusCode) {
                HttpStatus.OK -> TransferValidationResult.ALLOWED
                else -> TransferValidationResult.DENIED
            }
        } catch (e: HttpClientErrorException) {
            TransferValidationResult.DENIED
        }
    }

}
