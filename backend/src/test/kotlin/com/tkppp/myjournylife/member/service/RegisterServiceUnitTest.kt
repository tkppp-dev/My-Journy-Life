package com.tkppp.myjournylife.member.service

import com.tkppp.myjournylife.member.Member
import com.tkppp.myjournylife.member.MemberRepository
import com.tkppp.myjournylife.member.RegisterType
import com.tkppp.myjournylife.member.dto.register.LocalRegisterRequestDto
import net.nurigo.java_sdk.api.Message
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate

@ExtendWith(MockitoExtension::class)
class RegisterServiceUnitTest{
    @Mock
    lateinit var memberRepository: MemberRepository

    @Mock
    lateinit var redisTemplate: RedisTemplate<String, Any>

    @Mock
    lateinit var smsService: Message

    @Autowired
    @InjectMocks
    lateinit var registerService: RegisterService

    @Test
    fun localRegister() {
        val dto = LocalRegisterRequestDto(
            emailAddress = "test@test.com",
            password = "asdfasdfd",
            nickname = null,
            phoneNumber = "010-1234-1234",
        )

        val member = Member(
            id = 1L,
            emailAddress = "test@test.com",
            password = "asdfasdfd",
            nickname = null,
            phoneNumber = "010-1234-1234",
            registerType = RegisterType.LOCAL
        )

        Mockito.`when`(memberRepository.save(Mockito.any(Member::class.java))).thenReturn(member)

        assertThat(registerService.localRegister(dto)).isEqualTo(member.id)
    }
}