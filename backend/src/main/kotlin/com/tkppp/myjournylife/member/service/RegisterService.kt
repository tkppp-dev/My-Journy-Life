package com.tkppp.myjournylife.member.service

import com.tkppp.myjournylife.member.domain.MemberRepository
import com.tkppp.myjournylife.member.dto.register.LocalRegisterRequestDto
import com.tkppp.myjournylife.member.exception.DuplicatedEmailAddressException
import com.tkppp.myjournylife.member.exception.DuplicatedNicknameException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import net.nurigo.java_sdk.api.Message
import net.nurigo.java_sdk.exceptions.CoolsmsException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.concurrent.TimeUnit

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
                true -> throw DuplicatedNicknameException()
                false -> {}
            }
        }

        val encodedPassword = passwordEncoder.encode(localRegisterRequestDto.password)
        return try {
            memberRepository.save(localRegisterRequestDto.toEntity(encodedPassword)).id
        } catch (e: DataIntegrityViolationException){
            throw DuplicatedEmailAddressException()
        }
    }
}