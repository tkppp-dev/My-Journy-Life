package com.tkppp.myjournylife.review.controller

import com.tkppp.myjournylife.dto.review.DayReviewResponseDto
import com.tkppp.myjournylife.dto.review.DayReviewSaveRequestDto
import com.tkppp.myjournylife.dto.review.ReviewIntroListResponseDto
import com.tkppp.myjournylife.file.service.FileSaveService
import com.tkppp.myjournylife.member.domain.Member
import com.tkppp.myjournylife.review.service.ReviewLikeService
import com.tkppp.myjournylife.review.service.ReviewService
import com.tkppp.myjournylife.review.util.ReviewType
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/review")
class ReviewApiController(
    private val reviewService: ReviewService,
    private val fileSaveService: FileSaveService
) {

    @GetMapping("/day")
    fun returnDayReview(
        @RequestParam reviewId: Long,
        @RequestParam title: String
    ): ResponseEntity<DayReviewResponseDto> {
        val result = reviewService.getDayReview(reviewId, title)
        return ResponseEntity(result, HttpStatus.OK)
    }

    @GetMapping("/day/list")
    fun returnDayReviewIntro(): ResponseEntity<List<ReviewIntroListResponseDto>> {
        return ResponseEntity(reviewService.getDayReviewList(), HttpStatus.OK)
    }


    @Secured("ROLE_MEMBER")
    @PostMapping("/day")
    fun saveDayReview(
        @RequestHeader header: Map<String, Any>,
        @RequestBody requestDto: DayReviewSaveRequestDto
    ): ResponseEntity<Long> {
        val result = reviewService.saveDayReview(requestDto, header["authorization"] as String)
        fileSaveService.saveFileDataAtDatabase(requestDto.images, result["member"] as Member)
        return ResponseEntity(result["reviewId"] as Long, HttpStatus.OK)
    }
}