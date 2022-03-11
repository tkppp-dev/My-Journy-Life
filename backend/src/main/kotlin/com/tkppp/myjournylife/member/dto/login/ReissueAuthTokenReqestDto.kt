package com.tkppp.myjournylife.member.dto.login

data class ReissueAuthTokenReqestDto(
    val accessToken: String,
    val refreshToken: String
)
