package com.tkppp.myjournylife.member.service.impl

import com.tkppp.myjournylife.member.MemberRepository
import com.tkppp.myjournylife.member.dto.register.LocalRegisterRequestDto
import com.tkppp.myjournylife.member.service.RegisterService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RegisterServiceImpl(
    private val memberRepository: MemberRepository
) : RegisterService {
    @Transactional
    override fun localRegister(localRegisterRequestDto: LocalRegisterRequestDto): Long? {
        return memberRepository.save(localRegisterRequestDto.toEntity()).id
    }
}