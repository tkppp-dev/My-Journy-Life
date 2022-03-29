package com.tkppp.myjournylife.review.domain.day

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface DayReviewRepository : JpaRepository<DayReview, Long> {
    fun findFirst4ByOrderByIdDesc(): List<DayReview>?
    fun findAllByOrderByIdDesc(pageRequest: Pageable): Page<DayReview>
}