package com.tkppp.myjournylife.member.service

import com.tkppp.myjournylife.member.domain.MemberRepository
import com.tkppp.myjournylife.member.dto.member.MemberInfoResponseDto
import com.tkppp.myjournylife.member.exception.MemberNotFoundException
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository
) {

    fun getMemberInfo(emailAddress: String): MemberInfoResponseDto{
        return when(val member = memberRepository.findByEmailAddress(emailAddress)){
            null -> throw MemberNotFoundException()
            else -> MemberInfoResponseDto(
                emailAddress = member.emailAddress,
                nickname = member.nickname,
                registerType = member.registerType.name
            )
        }
    }
}