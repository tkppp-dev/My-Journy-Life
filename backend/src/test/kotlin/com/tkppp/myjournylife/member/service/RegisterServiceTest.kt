package com.tkppp.myjournylife.member.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.tkppp.myjournylife.member.Member
import com.tkppp.myjournylife.member.MemberRepository
import com.tkppp.myjournylife.member.RegisterType
import com.tkppp.myjournylife.member.dto.register.EmailDuplicationCheckResponseDto
import com.tkppp.myjournylife.member.dto.register.SmsRequestDto
import net.nurigo.java_sdk.api.Message
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.assertj.core.api.Assertions.assertThat
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.redis.core.RedisTemplate

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension::class)
class RegisterServiceTest(
    @Autowired private val memberRepository: MemberRepository,
    @Autowired private val mvc: MockMvc,
    @Autowired private val redisTemplate: RedisTemplate<String, Any>,
){
    @MockBean
    lateinit var smsService: Message

    val mapper = jacksonObjectMapper()

    @AfterEach
    fun tearDown(){
        memberRepository.deleteAll()
    }

    @Test
    fun emailDupCheck_shouldReturnFalse(){
        val email = "test@test.com"
        val responseDto = EmailDuplicationCheckResponseDto(false)

        mvc.perform(get("/api/register/duplication/${email}"))
            .andExpectAll(
                status().isOk,
                content().contentType(MediaType.APPLICATION_JSON),
                content().json(mapper.writeValueAsString(responseDto))
            )
    }

    @Test
    fun emailDupCheck_shouldReturnTrue(){
        val email = "test@test.com"
        val responseDto = EmailDuplicationCheckResponseDto(true)

        memberRepository.save(
            Member(
                emailAddress = email,
                password = "123123",
                phoneNumber = "010-1234-1234",
                registerType = RegisterType.LOCAL
            )
        )

        mvc.perform(get("/api/register/duplication/${email}"))
            .andExpectAll(
                status().isOk,
                content().contentType(MediaType.APPLICATION_JSON),
                content().json(mapper.writeValueAsString(responseDto))
            )
    }

    @Test
    fun sendSmsForMobileAuth_shouldSuccess(){
        //given
        val phoneNum = "010-1234-1234"
        val notRequestedPhoneNum = "010-1234-1111"

        //when
        mvc.perform(post("/api/register/phone-auth")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(SmsRequestDto(phoneNum)))
        ).andExpectAll(
            status().isOk
        )

        val valueOps = redisTemplate.opsForValue()

        Mockito.verify(smsService).send(Mockito.any())

        assertThat(valueOps.get("mobileAuth:$phoneNum"))
            .isInstanceOf(String::class.java)

        assertThat(valueOps.get("mobileAuth:$notRequestedPhoneNum"))
            .isNull()
    }

}