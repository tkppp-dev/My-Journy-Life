package com.tkppp.myjournylife.member.service

import com.tkppp.myjournylife.member.domain.Member
import com.tkppp.myjournylife.member.domain.MemberRepository
import com.tkppp.myjournylife.member.util.RegisterType
import com.tkppp.myjournylife.member.dto.register.LocalRegisterRequestDto
import net.nurigo.java_sdk.api.Message
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.assertj.core.api.Assertions.assertThat
import org.springframework.data.redis.core.RedisTemplate

@ExtendWith(MockitoExtension::class)
class RegisterServiceUnitTest{
    @Mock
    lateinit var memberRepository: MemberRepository

    @Mock
    lateinit var redisTemplate: RedisTemplate<String, String>

    @Mock
    lateinit var smsService: Message

    @InjectMocks
    lateinit var registerService: RegisterService

    @Test
    fun localRegister() {
        val dto = LocalRegisterRequestDto(
            emailAddress = "test@test.com",
            password = "asdfasdfd",
            nickname = null,
        )

        val member = Member(
            id = 1L,
            emailAddress = "test@test.com",
            password = "asdfasdfd",
            nickname = null,
            registerType = RegisterType.LOCAL
        )

        Mockito.`when`(memberRepository.save(Mockito.any(Member::class.java))).thenReturn(member)

        assertThat(registerService.localRegister(dto)).isEqualTo(member.id)
    }
}