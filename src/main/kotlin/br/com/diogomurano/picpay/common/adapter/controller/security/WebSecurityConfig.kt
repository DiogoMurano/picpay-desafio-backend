package br.com.diogomurano.picpay.common.adapter.controller.security

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import br.com.diogomurano.picpay.common.adapter.controller.security.authentication.AuthorizationHeaderAuthenticationFilter
import br.com.diogomurano.picpay.common.adapter.controller.security.authentication.ServiceAuthenticationProvider

@Configuration
@EnableWebSecurity
class WebSecurityConfig(
    private val authenticationProvider: ServiceAuthenticationProvider,
    private val authorizationHeaderAuthenticationFilter: AuthorizationHeaderAuthenticationFilter
): WebSecurityConfigurerAdapter() {

    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers(
            "/actuator/**",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/api/v1/users",
            "/api/v1/auth/login"
        )
    }

    override fun configure(http: HttpSecurity) {
        http.csrf()
            .disable()
            .authorizeHttpRequests()
            .anyRequest()
            .authenticated()
            .and()
            .addFilterAfter(authorizationHeaderAuthenticationFilter, BasicAuthenticationFilter::class.java)
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(authenticationProvider)
    }

}
