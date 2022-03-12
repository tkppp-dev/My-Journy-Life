package com.tkppp.myjournylife.member.dto.login

data class LoginResponseDto(
    val success: Boolean,
    val accessToken: String? = null,
    val refreshToken: String? = null
) {
}