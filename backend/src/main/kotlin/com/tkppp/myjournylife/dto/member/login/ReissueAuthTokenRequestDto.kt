package com.tkppp.myjournylife.dto.member.login

data class ReissueAuthTokenRequestDto(
    val emailAddress: String,
    val accessToken: String,
    val refreshToken: String
)
