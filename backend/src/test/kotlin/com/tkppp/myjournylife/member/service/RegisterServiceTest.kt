package com.tkppp.myjournylife.member.service

import com.tkppp.myjournylife.member.domain.Member
import com.tkppp.myjournylife.member.domain.MemberRepository
import com.tkppp.myjournylife.member.util.RegisterType
import com.tkppp.myjournylife.dto.member.register.LocalRegisterRequestDto
import com.tkppp.myjournylife.error.CustomException
import com.tkppp.myjournylife.error.ErrorCode
import com.tkppp.myjournylife.member.exception.DuplicatedEmailAddressException
import com.tkppp.myjournylife.member.exception.DuplicatedNicknameException
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@ExtendWith(MockitoExtension::class)
class RegisterServiceTest {
    val memberRepository: MemberRepository = Mockito.mock(MemberRepository::class.java)
    val passwordEncoder: BCryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder::class.java)
    val registerService = RegisterService(memberRepository, passwordEncoder)

    // given
    private val successEmail = "success@test.com"
    private val existedEmail = "existed@test.com"
    private val successNickname = "success_nickname"
    private val existedNickname = "existed_nickname"
    private val password = "password"
    private val encodedPassword = "encoded_password"
    private val registerType = RegisterType.LOCAL

    private val registerSuccessDto = LocalRegisterRequestDto(
        emailAddress = successEmail,
        password = password,
        nickname = successNickname,
    )

    private val registerFailureDtoWithExistedEmail = LocalRegisterRequestDto(
        emailAddress = existedEmail,
        password = password,
        nickname = null,
    )

    private val registerFailureDtoWithExistedNickname = LocalRegisterRequestDto(
        emailAddress = successEmail,
        password = password,
        nickname = existedNickname,
    )

    private val successReturnMember = Member(
        id = 1L,
        emailAddress = successEmail,
        password = encodedPassword,
        nickname = successNickname,
        registerType = registerType
    )

    @Nested
    inner class LocalRegisterTest {

        @Test
        @DisplayName("로컬 회원가입 성공")
        fun localRegister_shouldSuccess() {
            // stubbing
            Mockito.`when`(passwordEncoder.encode(Mockito.anyString())).thenReturn(encodedPassword)
            Mockito.`when`(memberRepository.findByNickname(successNickname)).thenReturn(null)
            Mockito.`when`(memberRepository.save(Mockito.any(Member::class.java))).thenReturn(successReturnMember)

            // when
            val result = registerService.localRegister(registerSuccessDto)

            // then
            assertThat(result).isEqualTo(1L)
            assertThat(result).isNotNull()
        }

        @Test
        @DisplayName("이미 회원가입된 이메일로 회원가입을 시도한다면 회원가입에 실패해야 한다.")
        fun localRegister_shouldFail_ExistedEmail() {
            // stubbing
            Mockito.`when`(passwordEncoder.encode(Mockito.anyString())).thenReturn(encodedPassword)
            Mockito.`when`(memberRepository.save(Mockito.any(Member::class.java))).thenThrow(DataIntegrityViolationException::class.java)

            // when, then
            val exception = assertThrows<CustomException> {
                registerService.localRegister(registerFailureDtoWithExistedEmail)
            }

            // then
            assertThat(exception.errorCode).isEqualTo(ErrorCode.DUPLICATED_EMAIL_ADDRESS)
        }

        @Test
        @DisplayName("이미 존재하는 별명으로 회원가입을 시도한다면 회원가입에 실패해야 한다.")
        fun localRegister_shouldFail_ExistedNickname() {
            // stubbing
            Mockito.`when`(memberRepository.findByNickname(existedNickname)).thenReturn(successReturnMember)

            // when
            val exception = assertThrows<CustomException> {
                registerService.localRegister(registerFailureDtoWithExistedNickname)
            }

            // then
            assertThat(exception.errorCode).isEqualTo(ErrorCode.DUPLICATED_NICKNAME)
        }
    }
}