package com.tkppp.myjournylife.dto.member.login

data class ReissueAuthTokenResponseDto(
    val accessToken: String? = null,
    val success: Boolean = true,
)