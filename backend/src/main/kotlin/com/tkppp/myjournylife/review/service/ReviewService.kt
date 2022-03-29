package com.tkppp.myjournylife.review.service

import com.tkppp.myjournylife.auth.util.JwtTokenProvider
import com.tkppp.myjournylife.dto.review.DayReviewResponseDto
import com.tkppp.myjournylife.dto.review.DayReviewSaveRequestDto
import com.tkppp.myjournylife.dto.review.ReviewIntroListResponseDto
import com.tkppp.myjournylife.dto.review.ReviewListResponseDto
import com.tkppp.myjournylife.error.CustomException
import com.tkppp.myjournylife.error.ErrorCode
import com.tkppp.myjournylife.member.domain.MemberRepository
import com.tkppp.myjournylife.review.domain.day.DayReviewRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.net.URLDecoder

@Service
class ReviewService(
    private val dayReviewRepository: DayReviewRepository,
    private val memberRepository: MemberRepository,
    private val jwtTokenProvider: JwtTokenProvider,
) {

    fun getDayReview(id: Long, title: String): DayReviewResponseDto {
        return when (val result = dayReviewRepository.findByIdOrNull(id)) {
            null -> throw CustomException(ErrorCode.INVALID_DATA)
            else -> {
                if (result.title == URLDecoder.decode(title, "UTF-8")) {
                    result.views += 1
                    dayReviewRepository.save(result)
                    DayReviewResponseDto(result)
                } else {
                    throw CustomException(ErrorCode.INVALID_DATA)
                }
            }
        }
    }

    fun getDayReviewIntroList(): List<ReviewIntroListResponseDto> {
        return dayReviewRepository.findFirst4ByOrderByIdDesc()?.map {
            ReviewIntroListResponseDto(
                it.id!!, it.title, it.member!!.nickname, it.createdDate, it.views, it.likeCount
            )
        } ?: mutableListOf()
    }

    fun getDayReviewListWithPaging(currentPage: Int): ReviewListResponseDto {
        val pageSize = 10
        val page = dayReviewRepository.findAllByOrderByIdDesc(PageRequest.of(currentPage, pageSize))
        val responseDto = page.content.map {
            ReviewIntroListResponseDto(
                it.id!!, it.title, it.member!!.nickname, it.createdDate, it.views, it.likeCount
            )
        }
        return ReviewListResponseDto(page.totalPages, page.number, responseDto)
    }

    @Transactional
    fun saveDayReview(requestDto: DayReviewSaveRequestDto, accessToken: String): Map<String, Any> {
        val email = jwtTokenProvider.getEmailAddress(accessToken)
        val member = memberRepository.findByEmailAddress(email)
        val entity = requestDto.toEntity()

        entity.member = member

        val reviewId = dayReviewRepository.save(entity)

        return hashMapOf(
            "reviewId" to reviewId.id!!,
            "member" to member!!
        )
    }
}