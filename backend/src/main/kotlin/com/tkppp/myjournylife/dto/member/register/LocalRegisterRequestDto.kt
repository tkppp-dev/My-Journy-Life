package com.tkppp.myjournylife.dto.member.register

import com.tkppp.myjournylife.member.domain.Member
import com.tkppp.myjournylife.member.util.RegisterType

data class LocalRegisterRequestDto(
    val emailAddress: String,
    val password: String,
    var nickname: String?,
){

    fun toEntity(password: String): Member {
        return Member(
            emailAddress = this.emailAddress,
            password = password,
            nickname = this.nickname,
            registerType = RegisterType.LOCAL
        )
    }
}
