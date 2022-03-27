package com.tkppp.myjournylife.review.domain

import com.tkppp.myjournylife.member.domain.Member
import org.hibernate.annotations.ColumnDefault
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
class Review(
    @Column(nullable = false, length = 100)
    var title: String,

    @Column(columnDefinition = "TEXT" ,nullable = false)
    var content: String,

    @Column(nullable = false)
    @ColumnDefault("0")
    var views: Long = 0,

    @Column(nullable = false)
    @ColumnDefault("0")
    var likeCount: Long = 0,

    @Column(nullable = false)
    @ColumnDefault("0")
    var dislikeCount: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    var member: Member? = null,

    @CreatedDate
    var createdDate: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    var modifiedDate: LocalDateTime = LocalDateTime.now(),
) {
}