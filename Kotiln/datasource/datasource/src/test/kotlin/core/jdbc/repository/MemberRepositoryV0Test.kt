package core.jdbc.repository

import core.jdbc.domain.Member
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class MemberRepositoryV0Test {

    private val memberRepository: MemberRepositoryV0 = MemberRepositoryV0()

    @AfterEach
    fun cleanUp() {
        memberRepository.deleteAll()
    }

    @Test
    fun `member 생성`() {
        val member = Member("A", 12_000)
        val savedMember = memberRepository.save(member)

        SoftAssertions.assertSoftly { softly ->
            softly.assertThat(savedMember.memberId).isEqualTo(member.memberId)
            softly.assertThat(savedMember.money).isEqualTo(member.money)
        }
    }

    @Test
    fun `member 수정`() {
        val updateMoney = 8_000
        val member = Member("A", 12_000)
        val savedMember = memberRepository.save(member)

        memberRepository.update(savedMember.memberId, updateMoney)
        val updatedMember = memberRepository.findById(savedMember.memberId)

        SoftAssertions.assertSoftly { softly ->
            softly.assertThat(updatedMember.money).isEqualTo(updateMoney)
        }
    }

    @Test
    fun `member 삭제`() {
        val member = Member("A", 12_000)
        val savedMember = memberRepository.save(member)

        memberRepository.delete(savedMember.memberId)

        assertThrows<NoSuchElementException> {
            memberRepository.findById(savedMember.memberId)
        }
    }

    @Test
    fun `member 조회`() {
        val member = Member("A", 12_000)
        val savedMember = memberRepository.save(member)
        val foundMember = memberRepository.findById(savedMember.memberId)

        SoftAssertions.assertSoftly { softly ->
            softly.assertThat(savedMember.memberId).isEqualTo(foundMember.memberId)
            softly.assertThat(savedMember.money).isEqualTo(foundMember.money)
        }
    }
}
