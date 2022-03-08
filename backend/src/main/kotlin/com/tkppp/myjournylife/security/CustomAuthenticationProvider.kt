package com.tkppp.myjournylife.security

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationProvider(
    private val userDetailsService: CustomUserDetailsService,
    private val passwordEncoder: BCryptPasswordEncoder
) : AuthenticationProvider {

    override fun authenticate(authentication: Authentication): Authentication {
        val userPrinciple = userDetailsService.loadUserByUsername(authentication.principal as String)

        return when(passwordEncoder.matches(authentication.credentials as String , userPrinciple.password)){
            true -> UsernamePasswordAuthenticationToken(userPrinciple, null, userPrinciple.authorities);
            false -> throw BadCredentialsException("Bad Credential")
        }
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return true
    }
}