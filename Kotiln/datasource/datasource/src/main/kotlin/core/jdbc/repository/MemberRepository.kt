package core.jdbc.repository

import core.jdbc.domain.Member

interface MemberRepository {

    fun save(member: Member): Member

    fun findById(memberId: String): Member

    fun update(memberId: String, money: Int)

    fun delete(memberId: String)
}
