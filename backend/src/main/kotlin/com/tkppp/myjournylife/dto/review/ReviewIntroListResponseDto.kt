package com.tkppp.myjournylife.dto.review

import java.time.LocalDateTime

data class ReviewIntroListResponseDto(
    val reviewId: Long,
    val title: String,
    val nickname: String?,
    val createdDate: LocalDateTime,
    val views: Long,
)