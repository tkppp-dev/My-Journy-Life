package com.tkppp.myjournylife.review.domain.day

import com.tkppp.myjournylife.member.domain.Member
import com.tkppp.myjournylife.review.domain.Review
import org.hibernate.annotations.ColumnDefault
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class DayReview(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, length = 50)
    var country: String,

    @Column(nullable = false, length = 50)
    var city: String,

    @Column(nullable = false, length = 100)
    var majorSpot: String,

    title: String,

    content: String,
) : Review(title, content) {

}