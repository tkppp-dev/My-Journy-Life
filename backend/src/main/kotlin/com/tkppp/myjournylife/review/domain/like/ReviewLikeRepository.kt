package com.tkppp.myjournylife.review.domain.like

import com.tkppp.myjournylife.review.util.ReviewType
import org.springframework.data.jpa.repository.JpaRepository

interface ReviewLikeRepository : JpaRepository<ReviewLike, Long> {
    fun findByReviewIdAndType(reviewId: Long, type: ReviewType): ReviewLike?
}