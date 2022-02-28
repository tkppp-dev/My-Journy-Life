package com.tkppp.myjournylife.member.service

import com.tkppp.myjournylife.member.dto.register.LocalRegisterRequestDto
import org.springframework.stereotype.Service

@Service
interface RegisterService {
    fun localRegister(localRegisterRequestDto: LocalRegisterRequestDto): Long?
}