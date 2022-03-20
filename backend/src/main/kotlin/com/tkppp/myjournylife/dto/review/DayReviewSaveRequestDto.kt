package com.tkppp.myjournylife.dto.review

import com.tkppp.myjournylife.review.domain.day.DayReview

data class DayReviewSaveRequestDto(
    val title: String,
    val country: String,
    val city: String,
    val majorSpot: String,
    val content: String,
    val images: List<String>,
) {
    fun toEntity(): DayReview{
        return DayReview(
            title = title,
            country = country,
            city = city,
            majorSpot = majorSpot,
            content = content
        )
    }
}