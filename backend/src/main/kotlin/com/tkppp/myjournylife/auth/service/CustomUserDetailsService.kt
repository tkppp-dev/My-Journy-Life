package com.tkppp.myjournylife.auth.service

import com.tkppp.myjournylife.auth.UserPrinciple
import com.tkppp.myjournylife.member.domain.MemberRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val memberRepository: MemberRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val member = memberRepository.findByEmailAddress(username) ?: throw UsernameNotFoundException("Cannot found username.")
        return UserPrinciple(member)
    }
}