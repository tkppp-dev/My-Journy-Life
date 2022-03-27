package com.tkppp.myjournylife.review.controller

import com.tkppp.myjournylife.dto.review.ReviewCommentRequestDto
import com.tkppp.myjournylife.dto.review.ReviewCommentResponseDto
import com.tkppp.myjournylife.review.domain.comment.ReviewComment
import com.tkppp.myjournylife.review.service.ReviewCommentService
import com.tkppp.myjournylife.review.util.ReviewType
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/comment")
class ReviewCommentApiController(
    private val reviewCommentService: ReviewCommentService
) {

    @GetMapping("/review/day/{reviewId}")
    fun getDayReviewComments(@PathVariable reviewId: Long): ResponseEntity<List<ReviewCommentResponseDto>?> {
        val comments = reviewCommentService.getComments(reviewId, ReviewType.DAY)
        return ResponseEntity(comments, HttpStatus.OK)
    }

    @PostMapping("/review/day")
    @Secured("ROLE_MEMBER")
    fun registerDayReviewComment(
        @RequestHeader header: Map<String, String>,
        @RequestBody requestDto: ReviewCommentRequestDto
    ): ResponseEntity<List<ReviewCommentResponseDto>?> {
        val comments = reviewCommentService.registerComment(requestDto, header["authorization"]!!, ReviewType.DAY)
        return ResponseEntity(comments, HttpStatus.OK)
    }
}