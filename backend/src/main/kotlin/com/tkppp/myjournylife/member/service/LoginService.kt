package com.tkppp.myjournylife.member.service

import com.tkppp.myjournylife.auth.util.JwtTokenProvider
import org.springframework.stereotype.Service

@Service
class LoginService(
    private val jwtTokenProvider: JwtTokenProvider
) {

}