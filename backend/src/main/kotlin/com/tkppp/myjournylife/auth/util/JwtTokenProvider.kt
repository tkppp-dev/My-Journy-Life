package com.tkppp.myjournylife.auth.util

import com.tkppp.myjournylife.auth.UserPrinciple
import com.tkppp.myjournylife.auth.service.CustomUserDetailsService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.lang.Exception
import java.lang.NullPointerException
import java.util.*
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletRequest

@Component
class JwtTokenProvider(
    private val userDetailsService: CustomUserDetailsService
) {
    companion object{
        private const val ACCESS_TOKEN_TTL = 30 * 60 * 1000   // 30min
        private const val REFRESH_TOKEN_TTL = 2 * 7 * 24 * 60 * 60 * 1000    // 2 week
    }

    var secretKey = "my-journey-life-token-secret-key"

    // 비밀키 암호화 - Base64
    @PostConstruct
    fun init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
    }

    fun createToken(authentication: Authentication, type: TokenType = TokenType.ACCESS_TOKEN): String {
        val now = Date()
        val principle = authentication.principal as UserPrinciple
        val ttl = when(type){
            TokenType.ACCESS_TOKEN -> ACCESS_TOKEN_TTL
            TokenType.REFRESH_TOKEN -> REFRESH_TOKEN_TTL
        }

        val claims = Jwts.claims().setSubject(principle.username)
        claims["name"] = type.name
        claims["roles"] = principle.authorities.first()

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(Date(now.time + ttl))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact()
    }

    fun getAuthentication(token: String) =
        userDetailsService.loadUserByUsername(getEmailAddress(token)).let {
            UsernamePasswordAuthenticationToken(it, it.password, it.authorities)
        }

    fun getEmailAddress(token: String): String {
        val prefix = "Bearer "
        var accessToken = token
        if(token.startsWith(prefix)) {
            accessToken = token.substring(prefix.length)
        }
        return Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(accessToken)
            .body
            .subject
    }

    fun resolveToken(req: HttpServletRequest): String?{
        val token = req.getHeader("Authorization") ?: ""
        val prefix = "Bearer "
        return when(token.startsWith(prefix)){
            true -> token.substring(prefix.length, token.length)
            false -> null
        }
    }

    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .let {
                    !it.body.expiration.before(Date())
                }
        } catch (e: RuntimeException){
            false
        }
    }

}