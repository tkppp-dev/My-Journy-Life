package com.tkppp.myjournylife.auth.auth_handler

import com.tkppp.myjournylife.auth.util.JwtTokenProvider
import com.tkppp.myjournylife.auth.util.TokenType
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
        val accessToken = jwtTokenProvider.createToken(authentication, TokenType.ACCESS_TOKEN)
        val refreshToken = jwtTokenProvider.createToken(authentication, TokenType.REFRESH_TOKEN)
        val redirectUrl = "http://13.209.36.27:8080/api/login/success?access=$accessToken&refresh=$refreshToken"
        response?.sendRedirect(redirectUrl)
    }
}