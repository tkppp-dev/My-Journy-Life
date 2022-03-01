package com.tkppp.myjournylife.member.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.tkppp.myjournylife.member.Member
import com.tkppp.myjournylife.member.MemberRepository
import com.tkppp.myjournylife.member.RegisterType
import com.tkppp.myjournylife.member.dto.register.EmailDuplicationCheckResponseDto
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class RegisterServiceTest(
    @Autowired private val memberRepository: MemberRepository,
    @Autowired private val mvc: MockMvc
){

    val mapper = jacksonObjectMapper()

    @AfterEach
    fun tearDown(){
        memberRepository.deleteAll()
    }

    @Test
    fun emailDupCheck_shouldReturnFalse(){
        val email = "test@test.com"
        val responseDto = EmailDuplicationCheckResponseDto(false)

        mvc.perform(get("/register/duplication/${email}"))
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

        mvc.perform(get("/register/duplication/${email}"))
            .andExpectAll(
                status().isOk,
                content().contentType(MediaType.APPLICATION_JSON),
                content().json(mapper.writeValueAsString(responseDto))
            )
    }
}