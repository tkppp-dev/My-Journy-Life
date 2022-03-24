package com.tkppp.myjournylife.dto.review

import com.tkppp.myjournylife.review.domain.day.DayReview

data class DayReviewResponseDto(
    val id: Long,
    val title: String,
    val country: String,
    val city: String,
    val majorSpot: String,
    val content: String,
) {

    constructor (dayReview: DayReview) : this(
        id = dayReview.id!!,
        title = dayReview.title,
        country = dayReview.country,
        city = dayReview.city,
        majorSpot = dayReview.majorSpot,
        content = dayReview.content
    )

}