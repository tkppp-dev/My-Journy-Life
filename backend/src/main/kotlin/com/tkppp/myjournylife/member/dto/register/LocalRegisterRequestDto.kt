package com.tkppp.myjournylife.member.dto.register

import com.tkppp.myjournylife.member.Member
import com.tkppp.myjournylife.member.RegisterType

data class LocalRegisterRequestDto(
    val emailAddress: String,
    val password: String,
    val nickname: String?,
    val phoneNumber: String,
){
    fun toEntity(): Member {
        return Member(
            emailAddress = this.emailAddress,
            password = this.password,
            nickname = this.nickname,
            phoneNumber = this.phoneNumber,
            registerType = RegisterType.LOCAL
        )
    }
}
