package com.tkppp.myjournylife.dto.review

import com.tkppp.myjournylife.review.domain.day.DayReview
import java.time.LocalDateTime

data class DayReviewResponseDto(
    val id: Long,
    val title: String,
    val country: String,
    val city: String,
    val majorSpot: String,
    val content: String,
    val views: Long,
    val createdDate: LocalDateTime,
    var member: Map<String, String?>? = null
) {

    constructor (dayReview: DayReview) : this(
        id = dayReview.id!!,
        title = dayReview.title,
        country = dayReview.country,
        city = dayReview.city,
        majorSpot = dayReview.majorSpot,
        content = dayReview.content,
        createdDate = dayReview.createdDate,
        views = dayReview.views
    ){
        member = hashMapOf(
            "emailAddress" to dayReview.member!!.emailAddress,
            "nickname" to dayReview.member!!.nickname
        )
    }

}