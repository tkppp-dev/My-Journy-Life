package com.tkppp.myjournylife.review.domain.day

import com.tkppp.myjournylife.member.domain.Member
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
class DayReview(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, length = 100)
    var title: String,

    @Column(nullable = false, length = 50)
    var country: String,

    @Column(nullable = false, length = 50)
    var city: String,

    @Column(nullable = false, length = 100)
    var majorSpot: String,

    @Column(columnDefinition = "TEXT" ,nullable = false)
    var content: String,

    @CreatedDate
    var createdDate: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    var modifiedDate: LocalDateTime = LocalDateTime.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    var member: Member? = null
) {

}