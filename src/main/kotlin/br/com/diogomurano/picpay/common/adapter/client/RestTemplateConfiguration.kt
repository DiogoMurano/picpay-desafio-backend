package br.com.diogomurano.picpay.common.adapter.client

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.web.client.RestTemplate

@Configuration
class RestTemplateConfiguration {

    @Bean
    fun restTemplate(): RestTemplate =
        RestTemplateBuilder()
            .build()

}

fun <T> RestTemplate.request(
    endpoint: String,
    httpMethod: HttpMethod,
    requestBody: T? = null,
    headers: Map<String, String>
) = exchange(
    endpoint,
    httpMethod,
    HttpEntity(
        requestBody,
        HttpHeaders().apply {
            headers.forEach { (key, value) -> add(key, value) }
        }
    ),
    Any::class.java
)
