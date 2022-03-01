package com.tkppp.myjournylife.member.controller

import com.tkppp.myjournylife.member.dto.register.*
import com.tkppp.myjournylife.member.service.RegisterService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/register")
class RegisterApiController(
    private val registerService: RegisterService
) {
    @PostMapping("")
    fun completeRegister(@RequestBody localRegisterRequestDto: LocalRegisterRequestDto): LocalRegisterResponseDto {
        val id = registerService.localRegister(localRegisterRequestDto)
        return if (id != null) {
            LocalRegisterResponseDto(true)
        } else {
            LocalRegisterResponseDto(false)
        }
    }

    @GetMapping("/duplication/{emailAddress}")
    fun emailAddressDuplicationCheck(@PathVariable emailAddress: String) =
        EmailDuplicationCheckResponseDto(registerService.emailAddressIsDuplicated(emailAddress))


    @PostMapping("/phone-number")
    fun requestSendingSms(@RequestBody smsRequestDto: SmsRequestDto) {
        registerService.sendSmsForMobileAuth(smsRequestDto.phoneNumber)
    }
}