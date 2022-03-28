package com.tkppp.myjournylife.reivew.domain

import com.tkppp.myjournylife.member.domain.Member
import com.tkppp.myjournylife.member.domain.MemberRepository
import com.tkppp.myjournylife.member.util.RegisterType
import com.tkppp.myjournylife.review.domain.day.DayReview
import com.tkppp.myjournylife.review.domain.day.DayReviewRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class DayReviewRepositoryTest(
    @Autowired private val memberRepository: MemberRepository,
    @Autowired private val dayReviewRepository: DayReviewRepository
) {

    private lateinit var joinMember: Member

    val title = "title"
    val country = "한국"
    val city = "서울"
    val majorSpot = "경복궁"
    val content = "content"

    @BeforeEach
    fun setup(){
        val email = "success@test.com"
        val member = Member(
            emailAddress = email,
            password = "password",
            nickname = null,
            registerType = RegisterType.LOCAL
        )
        joinMember = memberRepository.save(member)
    }

    @Test
    @DisplayName("여정 일기 저장이 성공한다.")
    fun saveDayReview_shouldSuccess(){
        // given
        val dayReview = DayReview(
            title = title,
            country = country,
            city = city,
            majorSpot = majorSpot,
            content = content,
        )
        dayReview.member = joinMember

        // when
        val result = dayReviewRepository.save(dayReview)

        // then
        assertThat(result.id).isGreaterThan(0)
        assertThat(result.title).isEqualTo(title)
        assertThat(result.country).isEqualTo(country)
        assertThat(result.city).isEqualTo(city)
        assertThat(result.majorSpot).isEqualTo(majorSpot)
        assertThat(result.content).isEqualTo(content)
        assertThat(result.views).isEqualTo(0)
        assertThat(result.member).isEqualTo(joinMember)
    }

    @Test
    @DisplayName("뒤에서 4개의 리뷰를 조회한다.")
    fun getFourDayReview_shouldSuccess(){
        // given
        for(i in 0..3){
            val dayReview = DayReview(
                title = title,
                country = country,
                city = city,
                majorSpot = majorSpot,
                content = content,
            )
            dayReview.member = joinMember
            dayReviewRepository.save(dayReview)
        }

        // when
        val result = dayReviewRepository.findFirst4ByOrderByIdDesc()

        //then
        assertThat(result?.first()!!.id).isEqualTo(4L)
        assertThat(result.last().id).isEqualTo(1L)
    }
}