package com.tkppp.myjournylife.member.service

import com.tkppp.myjournylife.auth.util.JwtTokenProvider
import com.tkppp.myjournylife.auth.util.TokenType
import com.tkppp.myjournylife.error.CustomException
import com.tkppp.myjournylife.error.ErrorCode
import com.tkppp.myjournylife.member.exception.ExpiredRefreshTokenException
import com.tkppp.myjournylife.member.exception.InvalidAccessTokenException
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class LoginService(
    private val jwtTokenProvider: JwtTokenProvider,
    private val redisTemplate: RedisTemplate<String, String>
) {

    private val hashOps = redisTemplate.opsForHash<String, String>()

    fun saveTokenAtCache(accessToken: String, refreshToken: String) {
        val key = "auth:login:${jwtTokenProvider.getEmailAddress(accessToken)}"
        val tokenData = hashMapOf(
            "accessToken" to accessToken,
            "refreshToken" to refreshToken
        )

        hashOps.putAll(key, tokenData)
        redisTemplate.expire(key, 14, TimeUnit.DAYS)
    }

    fun validateAccessToken(accessToken: String, refreshToken: String, key: String) {
        val storedAccessToken = hashOps.get(key, "accessToken")
        val storedRefreshToken = hashOps.get(key, "refreshToken")

        if (!jwtTokenProvider.validateToken(storedRefreshToken ?: "")) {
            throw CustomException(ErrorCode.REFRESH_TOKEN_EXPIRED)
        }

        if (accessToken != storedAccessToken) {
            throw CustomException(ErrorCode.INVALID_ACCESS_TOKEN)
        }
    }

    fun reissueAccessToken(accessToken: String, refreshToken: String): String {
        val key = "auth:login:${jwtTokenProvider.getEmailAddress(accessToken)}"
        validateAccessToken(accessToken, refreshToken, key)
        val authentication = jwtTokenProvider.getAuthentication(accessToken)
        val newAccessToken = jwtTokenProvider.createToken(authentication, TokenType.ACCESS_TOKEN)

        hashOps.put(key, "accessToken", newAccessToken)
        return newAccessToken
    }
}