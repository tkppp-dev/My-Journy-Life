package com.tkppp.myjournylife.member.domain

import com.tkppp.myjournylife.member.util.RegisterType
import org.springframework.beans.factory.annotation.Autowired
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.*
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.dao.DataIntegrityViolationException

@DataJpaTest
class MemberRepositoryTest(
    @Autowired private val memberRepository: MemberRepository
) {
    private val email = "test@test.com"
    private val password = "test-password"
    private val nickname = "test-nickname"
    private val registerType = RegisterType.LOCAL
    private val member = Member(
        emailAddress = email,
        password = password,
        nickname = nickname,
        registerType = registerType
    )

    @BeforeEach
    fun setup(){
        memberRepository.save(member)
    }

    @Nested
    @DisplayName("save() 테스트")
    inner class SaveTest{

        @Test
        @DisplayName("이미 존재하는 이메일, 별명을 저장할 경우 예외를 던진다")
        fun save_shouldThrowException(){
            // given
            val given = Member(
                emailAddress = email,
                password = "other-password",
                nickname = "other-nickname",
                registerType = registerType
            )

            // then
            val error = assertThrows<DataIntegrityViolationException> {
                memberRepository.save(given)
            }
            println(error.message)
        }
    }

    @Nested
    @DisplayName("findByEmailAddress() 테스트")
    inner class FindByEmailAddressTest{

        @Test
        @DisplayName("email 주소를 받아 일치하는 엔티티를 반환한다")
        fun findByEmailAddress_shouldReturnMember(){
            // when
            val result = memberRepository.findByEmailAddress(email)

            // then
            assertThat(result!!.emailAddress).isEqualTo(email)
            assertThat(result.password).isEqualTo(password)
            assertThat(result.nickname).isEqualTo(nickname)
            assertThat(result.registerType).isEqualTo(registerType)
        }

        @Test
        @DisplayName("email 주소를 받아 일치하는 이메일이 없으면 null 을 반환한다")
        fun findByEmailAddress_shouldReturnNull(){
            // given
            val notExistEmail = "notExist@test.com"

            // when
            val result = memberRepository.findByEmailAddress(notExistEmail)

            // then
            assertThat(result).isNull()
        }
    }

    @Nested
    @DisplayName("findByNickname() 테스트")
    inner class FindByNicknameTest{

        @Test
        @DisplayName("nickname 을 받아 일치하는 엔티티를 반환한다")
        fun findByNickname_shouldReturnMember(){
            // when
            val result = memberRepository.findByNickname(nickname)

            // then
            assertThat(result!!.emailAddress).isEqualTo(email)
            assertThat(result.password).isEqualTo(password)
            assertThat(result.nickname).isEqualTo(nickname)
            assertThat(result.registerType).isEqualTo(registerType)
        }

        @Test
        @DisplayName("nickname 을 받아 일치하는 nickname 이 없으면 null 을 반환한다")
        fun findByEmailAddress_shouldReturnNull(){
            // given
            val notExistNickname = "not-exist-nickname"

            // when
            val result = memberRepository.findByEmailAddress(notExistNickname)

            // then
            assertThat(result).isNull()
        }
    }
}