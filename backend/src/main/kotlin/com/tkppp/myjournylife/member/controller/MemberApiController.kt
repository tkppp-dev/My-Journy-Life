package com.tkppp.myjournylife.member.controller

import com.tkppp.myjournylife.member.dto.member.MemberInfoResponseDto
import com.tkppp.myjournylife.member.exception.EmailAddressNotFoundException
import com.tkppp.myjournylife.member.service.MemberService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/member")
class MemberApiController(
    private val memberService: MemberService
) {

    @GetMapping("/{emailAddress}")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun returnMemberInfo(@PathVariable emailAddress: String): MemberInfoResponseDto? {
        return try {
            memberService.getMemberInfo(emailAddress)
        } catch (e: EmailAddressNotFoundException){
            null
        }
    }
}