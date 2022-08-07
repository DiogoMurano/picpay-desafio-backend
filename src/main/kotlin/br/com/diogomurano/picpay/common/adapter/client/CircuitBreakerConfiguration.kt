package br.com.diogomurano.picpay.common.adapter.client

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.timelimiter.TimeLimiterConfig
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder
import org.springframework.cloud.client.circuitbreaker.Customizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.HttpClientErrorException
import java.time.Duration

@Configuration
class CircuitBreakerConfiguration {

    @Bean
    fun circuitBreakerConfig(): CircuitBreakerConfig = CircuitBreakerConfig
        .custom()
        .ignoreException { it is HttpClientErrorException.BadRequest }
        .failureRateThreshold(50f)
        .waitDurationInOpenState(Duration.ofMillis(1000))
        .slidingWindowSize(2)
        .build()

    @Bean
    fun timeLimiterConfig(): TimeLimiterConfig = TimeLimiterConfig
        .custom()
        .timeoutDuration(Duration.ofSeconds(15))
        .build()

    @Bean
    fun globalCustomConfiguration(): Customizer<Resilience4JCircuitBreakerFactory> {
        return Customizer<Resilience4JCircuitBreakerFactory> { factory ->
            factory.configureDefault { id ->
                Resilience4JConfigBuilder(id)
                    .timeLimiterConfig(timeLimiterConfig())
                    .circuitBreakerConfig(circuitBreakerConfig())
                    .build()
            }
        }
    }

}
