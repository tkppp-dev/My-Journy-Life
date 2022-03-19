package com.tkppp.myjournylife.dto.member.member

data class MemberInfoResponseDto(
    val emailAddress: String,
    val nickname: String?,
    val registerType: String
)
