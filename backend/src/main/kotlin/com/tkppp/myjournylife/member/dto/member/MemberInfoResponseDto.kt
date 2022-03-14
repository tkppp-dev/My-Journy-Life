package com.tkppp.myjournylife.member.dto.member

data class MemberInfoResponseDto(
    val emailAddress: String,
    val nickname: String?,
    val registerType: String
)
