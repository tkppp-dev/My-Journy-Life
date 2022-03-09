package com.tkppp.myjournylife.auth.auth_handler

import com.tkppp.myjournylife.auth.util.JwtTokenProvider
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CustomAuthenticationSuccessHandler(
    private val jwtTokenProvider: JwtTokenProvider
) : AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication
    ) {
        val jwt = jwtTokenProvider.createToken(authentication)
        response?.sendRedirect("http://localhost:8080/api/login/success/$jwt")
    }
}