package br.com.diogomurano.picpay.user.adapter.client

import br.com.diogomurano.picpay.common.adapter.client.request
import br.com.diogomurano.picpay.transaction.adapter.client.NotifyMessage
import br.com.diogomurano.picpay.user.domain.model.User
import br.com.diogomurano.picpay.user.domain.ports.output.NotifyUserGateway
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class NotifyUserGatewayImpl(
    private val restTemplate: RestTemplate,
    private val circuitBreakerFactory: CircuitBreakerFactory<*, *>,
    @Value("\${client.notify.sender-url}")
    private val notifyUserEndpoint: String
) : NotifyUserGateway {

    override fun notify(user: User, messageTitle: String, messageBody: String) {
        val circuitBreaker = circuitBreakerFactory.create("circuitbreaker")

        circuitBreaker.run {
            restTemplate.request(
                endpoint = notifyUserEndpoint,
                httpMethod = HttpMethod.POST,
                requestBody = NotifyMessage(
                    email = user.email,
                    messageTitle = messageTitle,
                    messageBody = messageBody
                ),
                headers = emptyMap()
            )
        }
    }

}
