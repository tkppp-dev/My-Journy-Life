package com.tkppp.myjournylife.review.domain.dislike

import com.tkppp.myjournylife.review.util.ReviewType
import org.springframework.data.jpa.repository.JpaRepository

interface ReviewDislikeRepository : JpaRepository<ReviewDislike, Long> {
    fun findByReviewIdAndType(reviewId: Long, type: ReviewType): ReviewDislike?
}