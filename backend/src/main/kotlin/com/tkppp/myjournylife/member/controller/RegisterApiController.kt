package com.tkppp.myjournylife.member.controller

import com.tkppp.myjournylife.member.dto.register.EmailDuplicationCheckRequestDto
import com.tkppp.myjournylife.member.dto.register.EmailDuplicationCheckResponseDto
import com.tkppp.myjournylife.member.dto.register.LocalRegisterRequestDto
import com.tkppp.myjournylife.member.dto.register.LocalRegisterResponseDto
import com.tkppp.myjournylife.member.service.RegisterService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/register")
class RegisterApiController(
    private val registerService: RegisterService
) {
    @PostMapping("/email")
    fun emailDuplicationCheck(@RequestBody emailDuplicationCheckRequestDto: EmailDuplicationCheckRequestDto): EmailDuplicationCheckResponseDto{
        return EmailDuplicationCheckResponseDto(false)
    }

    @PostMapping("")
    fun completeRegister(@RequestBody localRegisterRequestDto: LocalRegisterRequestDto): LocalRegisterResponseDto{
        val id = registerService.localRegister(localRegisterRequestDto)
        return if(id != null){
            LocalRegisterResponseDto(true)
        } else {
            LocalRegisterResponseDto(false)
        }
    }
}