package com.tkppp.myjournylife.review.service

import com.tkppp.myjournylife.auth.util.JwtTokenProvider
import com.tkppp.myjournylife.member.domain.MemberRepository
import com.tkppp.myjournylife.review.domain.day.DayReview
import com.tkppp.myjournylife.review.domain.day.DayReviewRepository
import com.tkppp.myjournylife.review.domain.dislike.ReviewDislike
import com.tkppp.myjournylife.review.domain.dislike.ReviewDislikeRepository
import com.tkppp.myjournylife.review.domain.like.ReviewLike
import com.tkppp.myjournylife.review.domain.like.ReviewLikeRepository
import com.tkppp.myjournylife.review.util.ReviewType
import com.tkppp.myjournylife.review.util.ReviewType.*
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.access.annotation.Secured
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReviewLikeService(
    private val dayReviewRepository: DayReviewRepository,
    private val memberRepository: MemberRepository,
    private val reviewLikeRepository: ReviewLikeRepository,
    private val reviewDislikeRepository: ReviewDislikeRepository,
    private val jwtTokenProvider: JwtTokenProvider,
) {

    fun getEmailAddress(accessToken: String): String = jwtTokenProvider.getEmailAddress(accessToken)

    fun getReviewEntity(reviewType: ReviewType, reviewId: Long) = when(reviewType){
        DAY -> dayReviewRepository.findByIdOrNull(reviewId)
        TRANSPORTATION -> dayReviewRepository.findByIdOrNull(reviewId)  // 엔티티 추가후 변경
        SPOT -> dayReviewRepository.findByIdOrNull(reviewId)    // 엔티티 추가후 변경
    }

    fun updateReviewEntity(reviewType: ReviewType, reviewEntity: Any) = when(reviewType){
        DAY -> dayReviewRepository.save(reviewEntity as DayReview)
        TRANSPORTATION -> dayReviewRepository.save(reviewEntity as DayReview) // 엔티티 추가후 변경
        SPOT -> dayReviewRepository.save(reviewEntity as DayReview)   // 엔티티 추가후 변경
    }

    fun updateReviewLikeCount(reviewType: ReviewType, reviewId: Long, offset: Long): Long{
        val reviewEntity = getReviewEntity(reviewType, reviewId)!!
        reviewEntity.likeCount += offset
        return updateReviewEntity(reviewType, reviewEntity).likeCount
    }

    fun updateReviewDislikeCount(reviewType: ReviewType, reviewId: Long, offset: Long): Long{
        val reviewEntity = getReviewEntity(reviewType, reviewId)!!
        reviewEntity.dislikeCount += offset
        return updateReviewEntity(reviewType, reviewEntity).dislikeCount
    }

    @Transactional
    fun updateReviewLikeCount(accessToken: String, reviewId: Long, reviewType: ReviewType): Long {
        return when (val reviewLike = reviewLikeRepository.findByReviewIdAndType(reviewId, DAY)) {
            null -> {
                val member = memberRepository.findByEmailAddress(getEmailAddress(accessToken))
                val likeEntity = ReviewLike(type = DAY, reviewId = reviewId, member = member)
                reviewLikeRepository.save(likeEntity)
                updateReviewLikeCount(reviewType, reviewId, 1L)
            }
            else -> {
                reviewLikeRepository.delete(reviewLike)
                updateReviewLikeCount(reviewType, reviewId, -1L)
            }
        }
    }

    @Transactional
    fun updateReviewDislikeCount(accessToken: String, reviewId: Long, reviewType: ReviewType): Long {
        return when (val reviewDislike = reviewDislikeRepository.findByReviewIdAndType(reviewId, DAY)) {
            null -> {
                val member = memberRepository.findByEmailAddress(getEmailAddress(accessToken))
                val dislikeEntity = ReviewDislike(type = DAY, reviewId = reviewId, member = member)
                reviewDislikeRepository.save(dislikeEntity)
                updateReviewDislikeCount(reviewType, reviewId, 1L)
            }
            else -> {
                reviewDislikeRepository.delete(reviewDislike)
                updateReviewDislikeCount(reviewType, reviewId, -1L)
            }
        }
    }
}