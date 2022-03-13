package com.tkppp.myjournylife.member.service

import com.tkppp.myjournylife.auth.util.JwtTokenProvider
import com.tkppp.myjournylife.auth.util.TokenType
import com.tkppp.myjournylife.member.exception.ExpiredRefreshTokenException
import com.tkppp.myjournylife.member.exception.InvalidAccessTokenException
import io.jsonwebtoken.Jwt
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
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
        @DisplayName("저장된 RefreshToken 이 만료되었다면 ExpiredRefreshTokenException 을 던져야 한다.")
        fun validateAccessToken_shouldThrowExpiredRefreshTokenException(){
            // stubbing
            Mockito.`when`(jwtTokenProvider.validateToken(refreshToken)).thenReturn(false)

            // when, then
            assertThrows<ExpiredRefreshTokenException> {
                loginService.validateAccessToken(accessToken, refreshToken, key)
            }
        }

        @Test
        @DisplayName("AccessToken 이 저장된 AccessToken 과 다르면 InvalidAccessTokenException 을 던져야 한다.")
        fun validateAccessToken_shouldThrowExpiredInvalidAccessTokenException(){
            // stubbing
            Mockito.`when`(jwtTokenProvider.validateToken(refreshToken)).thenReturn(true)

            // when, then
            assertThrows<InvalidAccessTokenException> {
                loginService.validateAccessToken(invalidAccessToken, refreshToken, key)
            }
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

            // stubbing
            Mockito.`when`(jwtTokenProvider.getEmailAddress(Mockito.anyString())).thenReturn(email)
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
            val result = loginService.reissueAccessToken(accessToken, refreshToken)
            assertThat(result).isEqualTo(reissuedAccessToken)
            assertThat(hashOps.get(key, "accessToken")).isEqualTo(reissuedAccessToken)
        }

        @Test
        @DisplayName("RefreshToken 이 만료되면 재발급이 실패해야 한다.")
        fun reissueAccessToken_shouldFailWithExpiredRefreshToken(){
            // stubbing
            Mockito.`when`(jwtTokenProvider.validateToken(refreshToken)).thenReturn(false)

            // then
            val result = loginService.reissueAccessToken(accessToken, refreshToken)
            assertThat(result).isNull()
        }

        @Test
        @DisplayName("AccessToken 이 저장된 AccessToken 과 다르면 재발급이 실패해야 한다.")
        fun reissueAccessToken_shouldFailWithInvalidAccessToken(){
            // stubbing
            Mockito.`when`(jwtTokenProvider.validateToken(refreshToken)).thenReturn(true)

            // then
            val result = loginService.reissueAccessToken(invalidAccessToken, refreshToken)
            assertThat(result).isNull()
        }
    }
}