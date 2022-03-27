package com.tkppp.myjournylife.member.service

import com.tkppp.myjournylife.auth.util.JwtTokenProvider
import com.tkppp.myjournylife.auth.util.TokenType
import com.tkppp.myjournylife.error.CustomException
import com.tkppp.myjournylife.error.ErrorCode
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.redis.core.RedisTemplate
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.*
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import java.util.concurrent.TimeUnit

@ExtendWith(MockitoExtension::class)
@DataRedisTest
class LoginServiceTest(
    @Autowired private val redisTemplate: RedisTemplate<String, String>
) {

    val jwtTokenProvider: JwtTokenProvider = Mockito.mock(JwtTokenProvider::class.java)
    val loginService: LoginService = LoginService(jwtTokenProvider, redisTemplate)

    // given
    val accessToken = "access-token"
    val invalidAccessToken = "invalid-access-token"
    val reissuedAccessToken = "reissue-access-token"
    val refreshToken = "refresh-token"
    val email = "test@test.com"
    val hashOps = redisTemplate.opsForHash<String, String>()
    val key = "auth:login:${email}"

    @Nested
    inner class SaveTokenAtCacheTest {

        @Test
        @DisplayName("인증 토큰이 레디스 캐시에 저장되어야 한다.")
        fun saveTokenAtCache_shouldSuccess(){
            // stubbing
            Mockito.`when`(jwtTokenProvider.getEmailAddress(accessToken)).thenReturn(email)

            // when
            loginService.saveTokenAtCache(accessToken, refreshToken)

            // then
            assertThat(hashOps.get(key, "accessToken")).isEqualTo(accessToken)
            assertThat(hashOps.get(key, "refreshToken")).isEqualTo(refreshToken)
            assertThat(redisTemplate.getExpire(key, TimeUnit.DAYS)).isLessThanOrEqualTo(14)

            // tear down
            redisTemplate.delete(key)
        }
    }

    @Nested
    inner class ValidateAccessTokenTest {

        @BeforeEach
        fun setup() {
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
        @DisplayName("저장된 RefreshToken 이 만료되었다면 CustomException 을 던져야 한다.")
        fun validateAccessToken_shouldThrowException_WithRefreshTokenExpiredErrorCode(){
            // stubbing
            Mockito.`when`(jwtTokenProvider.validateToken(refreshToken)).thenReturn(false)

            // when
            val exception = assertThrows<CustomException> {
                loginService.validateAccessToken(accessToken, refreshToken, key)
            }

            // then
            assertThat(exception.errorCode).isEqualTo(ErrorCode.REFRESH_TOKEN_EXPIRED)
        }

        @Test
        @DisplayName("AccessToken 이 저장된 AccessToken 과 다르면 CustomException 을 던져야 한다.")
        fun validateAccessToken_shouldThrowException_WithInvalidAccessTokenErrorCode(){
            // stubbing
            Mockito.`when`(jwtTokenProvider.validateToken(refreshToken)).thenReturn(true)

            // when
            val exception = assertThrows<CustomException> {
                loginService.validateAccessToken(invalidAccessToken, refreshToken, key)
            }

            // then
            assertThat(exception.errorCode).isEqualTo(ErrorCode.INVALID_ACCESS_TOKEN)
        }
    }

    @Nested
    inner class ReissueAccessTokenTest {

        @BeforeEach
        fun setup() {
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
        @DisplayName("AccessToken 재발급이 성공해야 한다.")
        fun reissueAccessToken_shouldSuccess() {
            // given
            val authentication = UsernamePasswordAuthenticationToken("","")

            // stubbing
            Mockito.`when`(jwtTokenProvider.validateToken(refreshToken)).thenReturn(true)
            Mockito.`when`(jwtTokenProvider.getAuthentication(accessToken)).thenReturn(authentication)
            Mockito.`when`(jwtTokenProvider.createToken(authentication, TokenType.ACCESS_TOKEN)).thenReturn(reissuedAccessToken)

            // when
            val result = loginService.reissueAccessToken(email, accessToken, refreshToken)
            assertThat(result).isEqualTo(reissuedAccessToken)
            assertThat(hashOps.get(key, "accessToken")).isEqualTo(reissuedAccessToken)
        }
    }
}