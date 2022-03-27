package com.tkppp.myjournylife.review.service

import com.tkppp.myjournylife.auth.util.JwtTokenProvider
import com.tkppp.myjournylife.dto.review.ReviewCommentRequestDto
import com.tkppp.myjournylife.dto.review.ReviewCommentResponseDto
import com.tkppp.myjournylife.member.domain.MemberRepository
import com.tkppp.myjournylife.review.domain.comment.ReviewComment
import com.tkppp.myjournylife.review.domain.comment.ReviewCommentRepository
import com.tkppp.myjournylife.review.util.ReviewType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReviewCommentService(
    private val memberRepository: MemberRepository,
    private val reviewCommentRepository: ReviewCommentRepository,
    private val jwtTokenProvider: JwtTokenProvider
) {

    fun getComments(reviewId: Long, reviewType: ReviewType): List<ReviewCommentResponseDto>? {
        val comments = reviewCommentRepository.findByReviewIdAndType(reviewId, reviewType)
        val list = mutableListOf<ReviewCommentResponseDto>()
        if (comments != null) {
            for (comment in comments) {
                list += ReviewCommentResponseDto(
                    commentId = comment.id!!,
                    comment = comment.comment,
                    createdDate = comment.createdDate,
                    memberId = comment.member?.id!!,
                    nickname = comment.member?.nickname
                )
            }
        }
        return list
    }

    @Transactional
    fun registerComment(
        requestDto: ReviewCommentRequestDto,
        accessToken: String,
        reviewType: ReviewType
    ): List<ReviewCommentResponseDto>? {
        val email = jwtTokenProvider.getEmailAddress(accessToken)
        val member = memberRepository.findByEmailAddress(email)
        val commentEntity = ReviewComment(
            comment = requestDto.comment,
            reviewId = requestDto.reviewId,
            type = reviewType,
            member = member
        )

        reviewCommentRepository.save(commentEntity)
        return getComments(requestDto.reviewId, reviewType)
    }
}