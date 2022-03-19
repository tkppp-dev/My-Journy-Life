package com.tkppp.myjournylife.member.service

import com.tkppp.myjournylife.member.domain.MemberRepository
import com.tkppp.myjournylife.dto.member.member.MemberInfoResponseDto
import com.tkppp.myjournylife.error.CustomException
import com.tkppp.myjournylife.error.ErrorCode
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository
) {

    fun getMemberInfo(emailAddress: String): MemberInfoResponseDto {
        return when(val member = memberRepository.findByEmailAddress(emailAddress)){
            null -> throw CustomException(ErrorCode.MEMBER_NOT_FOUND)
            else -> MemberInfoResponseDto(
                emailAddress = member.emailAddress,
                nickname = member.nickname,
                registerType = member.registerType.name
            )
        }
    }
}