package com.tkppp.myjournylife.member.dto.login

data class ReissueAuthTokenRequestDto(
    val accessToken: String,
    val refreshToken: String
)
