package com.tkppp.myjournylife.review.controller

import com.tkppp.myjournylife.review.service.ReviewLikeService
import com.tkppp.myjournylife.review.util.ReviewType
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/review")
class ReviewLikeApiController(
    private val reviewLikeService: ReviewLikeService
) {
    @Secured("ROLE_MEMBER")
    @PatchMapping("day/like/{reviewId}")
    fun updateReviewLike(
        @RequestHeader header: Map<String, String>,
        @PathVariable reviewId: Long
    ): ResponseEntity<Long> {
        val likeCount = reviewLikeService.updateReviewLikeCount(header["authorization"]!!, reviewId, ReviewType.DAY)
        return ResponseEntity(likeCount, HttpStatus.OK)
    }

    @Secured("ROLE_MEMBER")
    @PatchMapping("day/dislike/{reviewId}")
    fun updateReviewDislike(
        @RequestHeader header: Map<String, String>,
        @PathVariable reviewId: Long
    ): ResponseEntity<Long> {
        val likeCount = reviewLikeService.updateReviewDislikeCount(header["authorization"]!!, reviewId, ReviewType.DAY)
        return ResponseEntity(likeCount, HttpStatus.OK)
    }
}