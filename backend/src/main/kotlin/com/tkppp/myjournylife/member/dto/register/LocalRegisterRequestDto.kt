package com.tkppp.myjournylife.member.dto.register

import com.tkppp.myjournylife.member.domain.Member
import com.tkppp.myjournylife.member.util.RegisterType

data class LocalRegisterRequestDto(
    val emailAddress: String,
    val password: String,
    var nickname: String?,
){

    fun toEntity(password: String): Member {
        if(nickname == ""){
            nickname = null
        }
        return Member(
            emailAddress = this.emailAddress,
            password = this.password,
            nickname = this.nickname,
            registerType = RegisterType.LOCAL
        )
    }
}
