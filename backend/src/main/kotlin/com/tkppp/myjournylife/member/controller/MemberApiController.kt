package com.tkppp.myjournylife.member.controller

import com.tkppp.myjournylife.dto.member.member.MemberInfoResponseDto
import com.tkppp.myjournylife.member.service.MemberService
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/member")
class MemberApiController(
    private val memberService: MemberService,
    private val redisTemplate: RedisTemplate<String, String>
) {

    @GetMapping("/test")
    fun testRedisAtEc2(){
        val valueOps = redisTemplate.opsForValue()
        valueOps.set("test", "test")
    }

    @GetMapping("/{emailAddress}")
    @Secured("ROLE_MEMBER")
    fun returnMemberInfo(@PathVariable emailAddress: String): ResponseEntity<MemberInfoResponseDto> {
        return ResponseEntity(memberService.getMemberInfo(emailAddress), HttpStatus.OK)
    }
}