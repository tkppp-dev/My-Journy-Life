package com.tkppp.myjournylife.member.service

import com.tkppp.myjournylife.member.MemberRepository
import com.tkppp.myjournylife.member.dto.register.LocalRegisterRequestDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import net.nurigo.java_sdk.api.Message
import net.nurigo.java_sdk.exceptions.CoolsmsException
import org.springframework.data.redis.core.RedisTemplate
import java.util.concurrent.TimeUnit

@Service
class RegisterService(
    private val memberRepository: MemberRepository,
    private val redisTemplate: RedisTemplate<String, String>,
    private val smsService: Message
    ){

    @Transactional
    fun localRegister(localRegisterRequestDto: LocalRegisterRequestDto): Long? {
        return memberRepository.save(localRegisterRequestDto.toEntity()).id
    }

    @Transactional
    fun emailAddressIsDuplicated(emailAddress: String): Boolean{
        return when(memberRepository.findByEmailAddress(emailAddress)){
            null -> false;
            else -> true;
        }
    }

    @Transactional
    fun sendSmsForMobileAuth(phoneNumber: String, authNum: Int = (1000..9999).random()){
        val key = "auth:mobile:$phoneNumber"
        val params = hashMapOf(
            "to" to phoneNumber,
            "from" to "010-6778-2283",
            "type" to "SMS",
            "text" to "[My Journey Life] 휴대폰 인증번호란에 [$authNum]를 입력하세요."
        )

        try{
            smsService.send(params)
            val valueOps = redisTemplate.opsForValue()
            valueOps.set(key, authNum.toString())
            redisTemplate.expire(key, 5, TimeUnit.MINUTES)
        }catch (e: CoolsmsException){
            println(e.stackTrace)
        }
    }
}