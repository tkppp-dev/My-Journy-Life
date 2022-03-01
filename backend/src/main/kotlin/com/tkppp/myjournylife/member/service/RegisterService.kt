package com.tkppp.myjournylife.member.service

import com.tkppp.myjournylife.member.MemberRepository
import com.tkppp.myjournylife.member.dto.register.LocalRegisterRequestDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RegisterService(
    private val memberRepository: MemberRepository
){

    @Transactional
    fun localRegister(localRegisterRequestDto: LocalRegisterRequestDto): Long? {
        return memberRepository.save(localRegisterRequestDto.toEntity()).id
    }

    @Transactional
    fun sendSms(){

    }
}