package com.tkppp.myjournylife.member.controller

import com.tkppp.myjournylife.dto.ResponseDto
import com.tkppp.myjournylife.dto.member.logout.LogoutRequestDto
import com.tkppp.myjournylife.member.service.LogoutService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
    fun performLogout(@RequestBody logoutRequestDto: LogoutRequestDto): ResponseEntity<ResponseDto?> {
        return try {
            logoutService.deleteTokenAtCache(logoutRequestDto)
            ResponseEntity(HttpStatus.NO_CONTENT)
        } catch (e: Exception){
            ResponseEntity.internalServerError().body(null)
        }
    }
}