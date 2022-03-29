package com.tkppp.myjournylife.dto.review

class ReviewListResponseDto(
    val totalPages: Int,
    val currentPage: Int,
    val reviews: List<ReviewIntroListResponseDto>,
)