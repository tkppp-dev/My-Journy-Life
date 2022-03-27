package com.tkppp.myjournylife.dto.review

data class ReviewCommentRequestDto(
    val reviewId: Long,
    val comment: String,
)