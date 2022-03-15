package com.tkppp.myjournylife.dto.member.login

import com.tkppp.myjournylife.dto.ResponseDto

data class ReissueAuthTokenResponseDto(
    val accessToken: String? = null,
    val success: Boolean = true,
) : ResponseDto