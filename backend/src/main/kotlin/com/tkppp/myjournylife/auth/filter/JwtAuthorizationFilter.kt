package com.tkppp.myjournylife.auth.filter

import com.tkppp.myjournylife.auth.util.JwtTokenProvider
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthorizationFilter(
    private val jwtTokenProvider: JwtTokenProvider
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ){
        jwtTokenProvider.resolveToken(request).let {
            when(it != null && jwtTokenProvider.validateToken(it)) {
                true -> SecurityContextHolder.getContext().authentication = jwtTokenProvider.getAuthentication(it)
                false -> {}
            }
        }
        filterChain.doFilter(request, response)
    }
}