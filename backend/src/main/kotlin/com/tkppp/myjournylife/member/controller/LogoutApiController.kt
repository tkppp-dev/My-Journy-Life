package com.tkppp.myjournylife.member.controller

import com.tkppp.myjournylife.dto.member.logout.LogoutRequestDto
import com.tkppp.myjournylife.member.service.LogoutService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
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
    @Secured("ROLE_MEMBER")
    fun performLogout(@RequestBody logoutRequestDto: LogoutRequestDto): ResponseEntity<Unit?> {
        logoutService.deleteTokenAtCache(logoutRequestDto)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}