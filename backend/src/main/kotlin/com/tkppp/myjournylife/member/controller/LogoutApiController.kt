package com.tkppp.myjournylife.member.controller

import com.tkppp.myjournylife.member.dto.logout.LogoutRequestDto
import com.tkppp.myjournylife.member.dto.logout.LogoutResponseDto
import com.tkppp.myjournylife.member.service.LogoutService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/logout")
class LogoutApiController(
   private val logoutService: LogoutService
) {

    @PostMapping
    fun performLogout(@RequestBody logoutRequestDto: LogoutRequestDto): LogoutResponseDto{
        return try {
            logoutService.deleteTokenAtCache(logoutRequestDto)
            LogoutResponseDto()
        } catch (e: Exception){
            LogoutResponseDto(false)
        }

    }
}