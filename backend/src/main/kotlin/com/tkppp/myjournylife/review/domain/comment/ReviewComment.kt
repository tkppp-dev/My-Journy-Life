package com.tkppp.myjournylife.review.domain.comment

import com.tkppp.myjournylife.member.domain.Member
import com.tkppp.myjournylife.review.util.ReviewType
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
class ReviewComment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var comment: String,

    @Column(nullable = false)
    val reviewId: Long,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val type: ReviewType,

    @CreatedDate
    var createdDate: LocalDateTime = LocalDateTime.now(),

    @JoinColumn(name="member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    var member: Member? = null
) {

}