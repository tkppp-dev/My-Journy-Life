package com.tkppp.myjournylife.dto.member.member

import com.tkppp.myjournylife.dto.ResponseDto

data class MemberInfoResponseDto(
    val emailAddress: String,
    val nickname: String?,
    val registerType: String
) : ResponseDto
