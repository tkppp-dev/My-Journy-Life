package com.tkppp.myjournylife.review.controller

import com.tkppp.myjournylife.dto.review.DayReviewSaveRequestDto
import com.tkppp.myjournylife.file.service.FileSaveService
import com.tkppp.myjournylife.review.service.ReviewService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/review")
class ReviewApiController(
    private val reviewService: ReviewService,
    private val fileSaveService: FileSaveService
) {

    @PostMapping("/day")
    fun saveDayReview(@RequestHeader header: Map<String, Any> ,@RequestBody requestDto: DayReviewSaveRequestDto): ResponseEntity<Unit?>{
        val member = reviewService.saveDayReview(requestDto, header["authorization"] as String)
        fileSaveService.saveFileDataAtDatabase(requestDto.images, member)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}