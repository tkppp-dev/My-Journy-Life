package com.tkppp.myjournylife.dto.review

import java.time.LocalDateTime

data class ReviewCommentResponseDto(
    val commentId: Long,
    val comment: String,
    val createdDate: LocalDateTime,
    val memberId: Long,
    val nickname: String?
)