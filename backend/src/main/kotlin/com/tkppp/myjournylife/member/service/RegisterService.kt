package com.tkppp.myjournylife.member.service

import com.tkppp.myjournylife.member.domain.MemberRepository
import com.tkppp.myjournylife.dto.member.register.LocalRegisterRequestDto
import com.tkppp.myjournylife.error.CustomException
import com.tkppp.myjournylife.error.ErrorCode
import org.springframework.stereotype.Service
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Service
class RegisterService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: BCryptPasswordEncoder
    ){

    fun isDuplicateNickname(nickname: String): Boolean{
        return when(memberRepository.findByNickname(nickname)){
            null -> false
            else -> true
        }
    }

    fun localRegister(localRegisterRequestDto: LocalRegisterRequestDto): Long? {
        if(localRegisterRequestDto.nickname != null){
            when(isDuplicateNickname(localRegisterRequestDto.nickname!!)){
                true -> throw CustomException(ErrorCode.DUPLICATED_NICKNAME)
                false -> {}
            }
        }

        val encodedPassword = passwordEncoder.encode(localRegisterRequestDto.password)
        return try {
            memberRepository.save(localRegisterRequestDto.toEntity(encodedPassword)).id
        } catch (e: DataIntegrityViolationException){
            throw CustomException(ErrorCode.DUPLICATED_EMAIL_ADDRESS)
        }
    }
}