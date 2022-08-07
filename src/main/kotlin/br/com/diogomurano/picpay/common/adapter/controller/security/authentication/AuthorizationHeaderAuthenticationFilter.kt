package br.com.diogomurano.picpay.common.adapter.controller.security.authentication

import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.exceptions.JWTVerificationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthorizationHeaderAuthenticationFilter(
    private val jwtVerifier: JWTVerifier
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val requestPath = request.servletPath

        val requestAuthToken = request.getHeader(AUTHORIZATION_HEADER)
        val userId = getUserId(requestPath)
            ?: return clearSecurityContextAndDoFilter(request, response, filterChain)

        if (requestAuthToken != null) {
            if (verifyBearerToken(requestAuthToken.removePrefix("Bearer "), userId.toString())) {
                setSecurityContextAndDoFilter(request, response, filterChain, requestAuthToken)
                return
            }
        }
        clearSecurityContextAndDoFilter(request, response, filterChain)
    }

    private fun verifyBearerToken(token: String, userId: String) = try {
        val verify = jwtVerifier.verify(token)
        verify.subject == userId
    } catch (e: JWTVerificationException) {
        false
    }

    private fun getUserId(
        requestPath: String
    ): UUID? = try {
        if (requestPath.startsWith("/api/v1/users/")) {
            UUID.fromString(requestPath.split("/").getOrNull(4))
        } else null
    } catch (e: Exception) {
        null
    }

    private fun clearSecurityContextAndDoFilter(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        SecurityContextHolder.clearContext()
        filterChain.doFilter(request, response)
    }

    private fun setSecurityContextAndDoFilter(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
        requestApiKey: String
    ) {
        SecurityContextHolder.getContext().authentication = ServiceAuthentication(requestApiKey)
        filterChain.doFilter(request, response)
    }

    companion object {
        private const val AUTHORIZATION_HEADER = "Authorization"
    }

}
