package com.tkppp.myjournylife.security

import com.tkppp.myjournylife.member.Member
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserPrinciple(member: Member) : UserDetails {
    private val email = member.emailAddress
    private val password = member.password
    private val authorities = listOf<GrantedAuthority>(SimpleGrantedAuthority(member.role.name))

    override fun getAuthorities() = authorities

    override fun getPassword() = this.password

    override fun getUsername() = this.email

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}