package core.jdbc.repository

import core.jdbc.connection.DBConnectionUtil
import core.jdbc.domain.Member
import org.slf4j.LoggerFactory
import java.sql.Connection

class MemberRepositoryV0 : MemberRepository {
    private val log = LoggerFactory.getLogger(MemberRepositoryV0::class.java)
    override fun save(member: Member): Member {
        val sql = "insert into member(member_id, money) values (?, ?)"

        return getConnection().use { conn ->
            val pstmt = conn.prepareStatement(sql)
            pstmt.setString(1, member.memberId)
            pstmt.setInt(2, member.money)
            pstmt.execute()
            member
        }
    }

    override fun findById(memberId: String): Member {
        val sql = "select * from member where member_id = ?"

        getConnection().use { conn ->
            val pstmt = conn.prepareStatement(sql)
            pstmt.setString(1, memberId)

            val rs = pstmt.executeQuery()
            return if (rs.next()) {
                Member(rs.getString("member_id"), rs.getInt("money"))
            } else {
                throw NoSuchElementException("member not found memberId=$memberId")
            }
        }
    }

    override fun update(memberId: String, money: Int) {
        val sql = "update member set money = ? where member_id = ?"

        getConnection().use { conn ->
            val pstmt = conn.prepareStatement(sql)
            pstmt.setInt(1, money);
            pstmt.setString(2, memberId)
            val resultSize = pstmt.executeUpdate()
            log.info("resultSize={}", resultSize)
        }
    }

    override fun delete(memberId: String) {
        val sql = "delete from member where member_id = ?"

        getConnection().use { conn ->
            val pstmt = conn.prepareStatement(sql)
            pstmt.setString(1, memberId)
            val resultSize = pstmt.executeUpdate()
            log.info("resultSize={}", resultSize)
        }
    }

    fun deleteAll() {
        val sql = "delete from member"
        getConnection().use { conn ->
            val pstmt = conn.prepareStatement(sql)
            pstmt.executeUpdate()
        }
    }

    private fun getConnection(): Connection {
        return DBConnectionUtil.getConnection()
    }
}
