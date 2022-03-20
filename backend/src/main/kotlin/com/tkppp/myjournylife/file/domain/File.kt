package com.tkppp.myjournylife.file.domain

import com.tkppp.myjournylife.member.domain.Member
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDate
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
class File(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(length = 255, nullable = false)
    val filename: String,

    @CreatedDate
    @Column(nullable = false)
    var createdDate: LocalDate = LocalDate.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    var member: Member? = null,
) {

}