package com.tkppp.myjournylife.review.domain.dislike

import com.tkppp.myjournylife.member.domain.Member
import com.tkppp.myjournylife.review.util.ReviewType
import javax.persistence.*

@Entity
class ReviewDislike(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val type: ReviewType,

    @Column(nullable = false)
    val reviewId: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    var member: Member? = null
) {
}