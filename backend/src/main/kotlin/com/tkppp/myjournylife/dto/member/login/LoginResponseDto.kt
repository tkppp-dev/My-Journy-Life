package com.tkppp.myjournylife.dto.member.login

data class LoginResponseDto(
    val accessToken: String,
    val refreshToken: String
)