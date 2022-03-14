package com.tkppp.myjournylife.member.dto.login

data class ReissueAuthTokenResponseDto(
    val accessToken: String? = null,
    val success: Boolean = true,
) {
}