package com.tkppp.myjournylife.member.service

import com.tkppp.myjournylife.auth.util.JwtTokenProvider
import com.tkppp.myjournylife.member.dto.logout.LogoutRequestDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest
import org.springframework.data.redis.core.RedisTemplate

@ExtendWith(MockitoExtension::class)
@DataRedisTest
class LogoutServiceTest(
    @Autowired private val redisTemplate: RedisTemplate<String, String>
) {
    private val jwtTokenProvider: JwtTokenProvider = Mockito.mock(JwtTokenProvider::class.java)
    private val logoutService = LogoutService(redisTemplate, jwtTokenProvider)
    private val hashOps = redisTemplate.opsForHash<String, String>()

    // given
    private val accessToken = "access-token"
    private val refreshToken = "refresh-token"
    private val email = "success@test.com"
    private val invalidEmail = "invalid@test.com"
    private val key = "auth:login:$email"
    private val logoutRequestDto = LogoutRequestDto(accessToken)

    @BeforeEach
    fun setup(){
        hashOps.putAll(key, hashMapOf(
            "accessToken" to accessToken,
            "refreshToken" to refreshToken
        ))
    }

    @AfterEach
    fun tearDown(){
        redisTemplate.delete(key)
    }

    @Test
    @DisplayName("로그아웃 성공")
    fun deleteTokenAtCache_shouldSuccess() {
        // stubbing
        Mockito.`when`(jwtTokenProvider.getEmailAddress(accessToken)).thenReturn(email)

        // when
        logoutService.deleteTokenAtCache(logoutRequestDto)

        // then
        assertThat(redisTemplate.hasKey(key)).isFalse()
    }

    @Test
    @DisplayName("로그아웃 실패")
    fun deleteTokenAtCache_shouldFail() {
        // stubbing
        Mockito.`when`(jwtTokenProvider.getEmailAddress(accessToken)).thenReturn(invalidEmail)

        // when
        logoutService.deleteTokenAtCache(logoutRequestDto)

        // then
        assertThat(redisTemplate.hasKey(key)).isTrue()
    }
}