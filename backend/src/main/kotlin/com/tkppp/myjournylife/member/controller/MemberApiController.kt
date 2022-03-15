package com.tkppp.myjournylife.member.controller

import com.tkppp.myjournylife.dto.ResponseDto
import com.tkppp.myjournylife.dto.error.ErrorResponseDto
import com.tkppp.myjournylife.dto.member.member.MemberInfoResponseDto
import com.tkppp.myjournylife.member.exception.MemberNotFoundException
import com.tkppp.myjournylife.member.service.MemberService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/member")
class MemberApiController(
    private val memberService: MemberService
) {

    @GetMapping("/{emailAddress}")
    fun returnMemberInfo(@PathVariable emailAddress: String): ResponseEntity<ResponseDto?> {
        return try {
            ResponseEntity.ok(memberService.getMemberInfo(emailAddress))
        } catch (e: MemberNotFoundException) {
            ResponseEntity(
                ErrorResponseDto(
                    "MEMBER_NOT_FOUND", e.message
                ), HttpStatus.NOT_FOUND
            )
        }
    }
}