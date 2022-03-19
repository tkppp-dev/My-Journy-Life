package com.tkppp.myjournylife.member.controller

import com.tkppp.myjournylife.dto.member.register.LocalRegisterRequestDto
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
    fun completeRegister(@RequestBody localRegisterRequestDto: LocalRegisterRequestDto): ResponseEntity<Unit?> {
        registerService.localRegister(localRegisterRequestDto)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}