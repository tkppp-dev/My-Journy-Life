package com.tkppp.myjournylife.member.controller

import com.tkppp.myjournylife.dto.ResponseDto
import com.tkppp.myjournylife.dto.error.ErrorResponseDto
import com.tkppp.myjournylife.dto.member.register.LocalRegisterRequestDto
import com.tkppp.myjournylife.dto.member.register.LocalRegisterResponseDto
import com.tkppp.myjournylife.member.exception.DuplicatedEmailAddressException
import com.tkppp.myjournylife.member.exception.DuplicatedNicknameException
import com.tkppp.myjournylife.member.service.RegisterService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/register")
class RegisterApiController(
    private val registerService: RegisterService,
) {
    @PostMapping("")
    fun completeRegister(@RequestBody localRegisterRequestDto: LocalRegisterRequestDto): ResponseEntity<ResponseDto?> {
        return try {
            registerService.localRegister(localRegisterRequestDto)
            ResponseEntity(HttpStatus.NO_CONTENT)
        } catch (e: DuplicatedEmailAddressException) {
            ResponseEntity(
                ErrorResponseDto(
                    "DUPLICATED_EMAIL_ADDRESS", e.message
                ), HttpStatus.CONFLICT
            )
        } catch (e: DuplicatedNicknameException) {
            ResponseEntity(
                ErrorResponseDto(
                    "DUPLICATED_NICKNAME", e.message
                ), HttpStatus.CONFLICT
            )
        }
    }
}