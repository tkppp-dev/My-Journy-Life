package com.tkppp.myjournylife.review.domain.comment

import com.tkppp.myjournylife.review.util.ReviewType
import org.springframework.data.jpa.repository.JpaRepository

interface ReviewCommentRepository : JpaRepository<ReviewComment, Long> {
    fun findByReviewIdAndType(reviewId: Long, type:ReviewType): List<ReviewComment>?
}