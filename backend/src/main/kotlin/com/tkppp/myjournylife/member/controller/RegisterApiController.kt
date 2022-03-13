package com.tkppp.myjournylife.member.controller

import com.tkppp.myjournylife.member.dto.register.*
import com.tkppp.myjournylife.member.exception.DuplicatedEmailAddressException
import com.tkppp.myjournylife.member.exception.DuplicatedNicknameException
import com.tkppp.myjournylife.member.service.RegisterService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/register")
class RegisterApiController(
    private val registerService: RegisterService,
) {
    @PostMapping("")
    fun completeRegister(@RequestBody localRegisterRequestDto: LocalRegisterRequestDto): LocalRegisterResponseDto {
        return try {
            registerService.localRegister(localRegisterRequestDto)
            LocalRegisterResponseDto(true)
        } catch(e: DuplicatedEmailAddressException){
            LocalRegisterResponseDto(false, "DUPLICATED_EMAIL")
        }   catch (ex: DuplicatedNicknameException){
            LocalRegisterResponseDto(false, "DUPLICATED_NICKNAME")
        }
    }
}