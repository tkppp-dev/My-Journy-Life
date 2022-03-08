package com.tkppp.myjournylife.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.lang.Exception
import java.util.*
import javax.annotation.PostConstruct
import javax.servlet.http.HttpServletRequest

@Component
class JwtTokenProvider(
    private val userDetailsService: CustomUserDetailsService
) {
    companion object{
        private const val TOKEN_TTL = 30 * 60 * 1000   // ms
    }
    private var secretKey = "testing-secret-key"

    // 비밀키 암호화 - Base64
    @PostConstruct
    fun init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
    }

    fun createToken(authentication: Authentication): String {
        val now = Date()
        val principle = authentication.principal as UserPrinciple
        val claims = Jwts.claims().setSubject(principle.username)
        claims["roles"] = principle.authorities.first()

        println("create token")

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(Date(now.time + TOKEN_TTL))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact()
    }

    fun getAuthentication(token: String) =
        userDetailsService.loadUserByUsername(getEmailAddress(token)).let {
            UsernamePasswordAuthenticationToken(it, it.password, it.authorities)
        }

    fun getEmailAddress(token: String): String =
        Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .body
            .subject

    fun resolveToken(req: HttpServletRequest) =
        req.getHeader("X-AUTH-TOKEN").let {
            when (it.startsWith("Bearer ")) {
                true -> it.substring("Bearer ".length, it.length)
                false -> null
            }
        }

    fun validateToken(jwtToken: String): Boolean {
        return try {
            Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwtToken)
                .let { !it.body.expiration.before(Date())}
        } catch (e: Exception){
            false
        }
    }

}