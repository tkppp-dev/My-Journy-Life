package com.tkppp.myjournylife.review.service

import com.tkppp.myjournylife.auth.util.JwtTokenProvider
import com.tkppp.myjournylife.dto.review.DayReviewSaveRequestDto
import com.tkppp.myjournylife.error.CustomException
import com.tkppp.myjournylife.error.ErrorCode
import com.tkppp.myjournylife.member.domain.Member
import com.tkppp.myjournylife.member.domain.MemberRepository
import com.tkppp.myjournylife.review.domain.day.DayReviewRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReviewService(
    private val dayReviewRepository: DayReviewRepository,
    private val memberRepository: MemberRepository,
    private val jwtTokenProvider: JwtTokenProvider
) {

    @Transactional
    fun saveDayReview(requestDto: DayReviewSaveRequestDto, token: String): Member {
        val accessToken = token.substring("Bearer ".length)
        val email = jwtTokenProvider.getEmailAddress(accessToken)
        val member = memberRepository.findByEmailAddress(email)
        val entity = requestDto.toEntity()

        entity.member = member

        dayReviewRepository.save(entity)

        return member!!
    }
}