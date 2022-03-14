package com.tkppp.myjournylife.member.service

import com.tkppp.myjournylife.auth.util.JwtTokenProvider
import com.tkppp.myjournylife.member.dto.logout.LogoutRequestDto
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service

@Service
class LogoutService(
    private val redisTemplate: RedisTemplate<String, String>,
    private val jwtTokenProvider: JwtTokenProvider
) {

    fun deleteTokenAtCache(logoutRequestDto: LogoutRequestDto){
        val email = jwtTokenProvider.getEmailAddress(logoutRequestDto.accessToken)

        redisTemplate.delete("auth:login:$email")
    }
}