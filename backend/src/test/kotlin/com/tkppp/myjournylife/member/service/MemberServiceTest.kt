package com.tkppp.myjournylife.member.service

import com.tkppp.myjournylife.member.domain.Member
import com.tkppp.myjournylife.member.domain.MemberRepository
import com.tkppp.myjournylife.member.exception.MemberNotFoundException
import com.tkppp.myjournylife.member.util.RegisterType
import com.tkppp.myjournylife.member.util.RoleType
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows

@ExtendWith(MockitoExtension::class)
class MemberServiceTest {

    private val memberRepository: MemberRepository = Mockito.mock(MemberRepository::class.java)
    private val memberService: MemberService = MemberService(memberRepository)

    @Nested
    inner class GetMemberInfoTest {

        // given
        private val existEmail = "exist@test.com"
        private val notExistEmail = "not-exist@test.com"
        private val nickname = "success"
        private val registerType = RegisterType.LOCAL.name
        private val member = Member(
            id = 1L,
            emailAddress = existEmail,
            password = "password",
            nickname = nickname,
            role = RoleType.ROLE_MEMBER,
            registerType = RegisterType.LOCAL
        )


        @Test
        @DisplayName("이메일이 일치하는 회원이 있다면 회원 정보를 반환한다.")
        fun getMemberInfo_shouldSuccess() {
            // stubbing
            Mockito.`when`(memberRepository.findByEmailAddress(existEmail)).thenReturn(member)

            // when
            val result = memberService.getMemberInfo(existEmail)

            assertThat(result.emailAddress).isEqualTo(existEmail)
            assertThat(result.nickname).isEqualTo(nickname)
            assertThat(result.registerType).isEqualTo(registerType)
        }

        @Test
        @DisplayName("이메일이 일치하는 회원이 없다면 MemberNotFoundException 을 던진다.")
        fun getMemberInfo_shouldThrowMemberNotFoundException() {
            // stubbing
            Mockito.`when`(memberRepository.findByEmailAddress(notExistEmail)).thenReturn(null)

            // when, then
            assertThrows<MemberNotFoundException> {
                memberService.getMemberInfo(notExistEmail)
            }
        }
    }

}