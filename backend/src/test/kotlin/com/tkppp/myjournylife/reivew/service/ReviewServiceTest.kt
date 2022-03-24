package com.tkppp.myjournylife.reivew.service

import com.tkppp.myjournylife.auth.util.JwtTokenProvider
import com.tkppp.myjournylife.dto.review.DayReviewResponseDto
import com.tkppp.myjournylife.dto.review.DayReviewSaveRequestDto
import com.tkppp.myjournylife.error.CustomException
import com.tkppp.myjournylife.error.ErrorCode
import com.tkppp.myjournylife.member.domain.Member
import com.tkppp.myjournylife.member.domain.MemberRepository
import com.tkppp.myjournylife.member.util.RegisterType
import com.tkppp.myjournylife.review.domain.day.DayReview
import com.tkppp.myjournylife.review.domain.day.DayReviewRepository
import com.tkppp.myjournylife.review.service.ReviewService
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.repository.findByIdOrNull
import java.net.URLEncoder

@ExtendWith(MockKExtension::class)
class ReviewServiceTest {
    // mocking
    private val dayReviewRepository = mockk<DayReviewRepository>()
    private val memberRepository = mockk<MemberRepository>()
    private val jwtTokenProvider = mockk<JwtTokenProvider>()

    // inject mocks
    private val reviewService = ReviewService(dayReviewRepository, memberRepository, jwtTokenProvider)

    @Nested
    @DisplayName("saveDayReview 테스트")
    inner class SaveDayReviewTest {
        // given
        private val accessToken = "access-token"
        private val email = "success@test.com"
        private val member = Member(
            id = 1L,
            emailAddress = email,
            password = "password",
            registerType = RegisterType.LOCAL
        )
        private val saveRequestDto = DayReviewSaveRequestDto(
            title = "제목",
            content = "내용",
            country = "국가",
            city = "도시",
            majorSpot = "여행지",
            images = listOf(),
        )

        @Test
        @DisplayName("여정일기 저장이 성공해야 한다.")
        fun saveDayReview_shouldSuccess() {
            // stubbing
            every { jwtTokenProvider.getEmailAddress(accessToken) } returns email
            every { memberRepository.findByEmailAddress(any()) } returns member
            every { dayReviewRepository.save(any()) } returns DayReview(1, "", "", "", "", "")

            // when
            val result = reviewService.saveDayReview(saveRequestDto, accessToken)

            // then
            assertThat(result["member"] as Member).isEqualTo(member)
            assertThat(result["reviewId"] as Long).isGreaterThanOrEqualTo(1)
            verify(exactly = 1) { jwtTokenProvider.getEmailAddress(accessToken) }
            verify(exactly = 1) { memberRepository.findByEmailAddress(any()) }
            verify(exactly = 1) { dayReviewRepository.save(any()) }
        }
    }

    @Nested
    @DisplayName("getDayReview 테스트")
    inner class GetDayReviewTest {

        @Test
        @DisplayName("DayReviewResponseDto 를 반환하는데 성공해야 한다.")
        fun getDayReview_shouldSuccess() {
            // given
            val id = 1L
            val title = URLEncoder.encode("제목", "UTF-8")
            val entity = DayReview(
                id = 1L,
                title = "제목",
                content = "내용",
                country = "국가",
                city = "도시",
                majorSpot = "여행지"
            )
            val responseDto = DayReviewResponseDto(entity)

            // stubbing
            every { dayReviewRepository.findByIdOrNull(id) } returns entity

            // when
            val result = reviewService.getDayReview(id, title)

            // then
            assertThat(result).isEqualTo(responseDto)
        }

        @Test
        @DisplayName("올바르지 않은 reviewId 를 전달받을 경우 CustomException 을 던져야 한다")
        fun getDayReview_shouldThrowExceptionWithInvalidId() {
            // given
            val id = 1L
            val title = URLEncoder.encode("제목", "UTF-8")

            // stubbing
            every { dayReviewRepository.findByIdOrNull(id) } returns null

            // when
            val ex = assertThrows<CustomException> { reviewService.getDayReview(id, title) }

            // then
            assertThat(ex.errorCode).isEqualTo(ErrorCode.INVALID_DATA)
        }

        @Test
        @DisplayName("올바르지 않은 title 을 전달받을 경우 CustomException 을 던져야 한다")
        fun getDayReview_shouldThrowExceptionWithInvalidTitle() {
            // given
            val id = 1L
            val title = URLEncoder.encode("다른 제목", "UTF-8")
            val entity = DayReview(
                id = 1L,
                title = "제목",
                content = "내용",
                country = "국가",
                city = "도시",
                majorSpot = "여행지"
            )

            // stubbing
            every { dayReviewRepository.findByIdOrNull(id) } returns entity

            // when
            val ex = assertThrows<CustomException> { reviewService.getDayReview(id, title) }

            // then
            assertThat(ex.errorCode).isEqualTo(ErrorCode.INVALID_DATA)
        }
    }
}