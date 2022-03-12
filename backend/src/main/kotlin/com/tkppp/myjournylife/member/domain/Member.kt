package com.tkppp.myjournylife.member.domain

import com.tkppp.myjournylife.member.util.RegisterType
import com.tkppp.myjournylife.member.util.RoleType
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.DynamicInsert
import javax.persistence.*

@Entity
@DynamicInsert
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(length = 255, nullable = false, unique = true)
    val emailAddress: String,

    @Column(length = 60)
    @ColumnDefault("null")
    val password: String? = null,

    @Column(length = 16, unique = true)
    @ColumnDefault("null")
    var nickname: String? = null,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var role: RoleType = RoleType.ROLE_MEMBER,

    @Enumerated(EnumType.STRING)
    val registerType: RegisterType
) {

}