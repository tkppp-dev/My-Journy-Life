package com.tkppp.myjournylife.member.domain

import com.tkppp.myjournylife.member.domain.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByEmailAddress(emailAddress: String): Member?
}