package com.tkppp.myjournylife.member

import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.DynamicInsert
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
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

    @Column(length = 13, nullable = false)
    val phoneNumber: String,

    @Enumerated(EnumType.STRING)
    val registerType: RegisterType
) {

}