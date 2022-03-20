package com.tkppp.myjournylife.reivew.service

import com.tkppp.myjournylife.auth.util.JwtTokenProvider
import com.tkppp.myjournylife.dto.review.DayReviewSaveRequestDto
import com.tkppp.myjournylife.member.domain.Member
import com.tkppp.myjournylife.member.domain.MemberRepository
import com.tkppp.myjournylife.member.util.RegisterType
import com.tkppp.myjournylife.review.domain.day.DayReview
import com.tkppp.myjournylife.review.domain.day.DayReviewRepository
import com.tkppp.myjournylife.review.service.ReviewService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class ReviewServiceTest {
    // mocking
    private val dayReviewRepository = Mockito.mock(DayReviewRepository::class.java)
    private val memberRepository = Mockito.mock(MemberRepository::class.java)
    private val jwtTokenProvider = Mockito.mock(JwtTokenProvider::class.java)
    // inject mocks
    private val reviewService = ReviewService(dayReviewRepository, memberRepository, jwtTokenProvider)


    @Nested
    @DisplayName("saveDayReview 테스트")
    inner class SaveDayReviewTest {
        // given
        private val requestDto = DayReviewSaveRequestDto(
            title = "title",
            content = "content",
            country = "country",
            city = "city",
            majorSpot = "majorSpot",
            images = listOf(),
        )
        private val accessToken = "access-token"
        private val email = "success@test.com"
        private val member = Member(
            id = 1L,
            emailAddress = email,
            password = "password",
            registerType = RegisterType.LOCAL
        )

        @Test
        @DisplayName("여정일기 저장이 성공해야 한다.")
        fun saveDayReview_shouldSuccess() {
            // stubbing
            `when`(jwtTokenProvider.getEmailAddress(accessToken)).thenReturn(email)
            `when`(memberRepository.findByEmailAddress(anyString())).thenReturn(member)

            // when
            val result = reviewService.saveDayReview(requestDto, accessToken)

            // then
            assertThat(result).isEqualTo(member)
            verify(jwtTokenProvider, times(1)).getEmailAddress(accessToken)
            verify(memberRepository, times(1)).findByEmailAddress(anyString())
            verify(dayReviewRepository, times(1)).save(any(DayReview::class.java))
        }

    }

}