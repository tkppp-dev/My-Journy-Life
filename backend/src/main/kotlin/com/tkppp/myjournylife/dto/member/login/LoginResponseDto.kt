package com.tkppp.myjournylife.dto.member.login

import com.tkppp.myjournylife.dto.ResponseDto

data class LoginResponseDto(
    val accessToken: String,
    val refreshToken: String
) : ResponseDto