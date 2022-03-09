package com.tkppp.myjournylife.member.dto.register

import com.tkppp.myjournylife.member.domain.Member
import com.tkppp.myjournylife.member.util.RegisterType

data class LocalRegisterRequestDto(
    val emailAddress: String,
    val password: String,
    val nickname: String?,
    val phoneNumber: String,
){

    fun toEntity(password: String): Member {
        return Member(
            emailAddress = this.emailAddress,
            password = password,
            nickname = this.nickname,
            phoneNumber = this.phoneNumber,
            registerType = RegisterType.LOCAL
        )
    }
}
