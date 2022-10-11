package com.example.core.l_tip

import com.example.core.a_kotlinBasic.iterator.printLine
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.runBlocking
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration
import kotlin.concurrent.timer

const val TIME_DELAY_MS = 100L

class User(
    val id: Long,
    val name: String
) {
    override fun toString(): String {
        return "User($id=id, $name=name)"
    }
}

interface UserAsyncRepository {
    fun findUserByIdAsMono(userId: Long): Mono<User>
    fun findAllUserByIdsAsFlux(userIds: List<Long>): Flux<User>
}

class UserRepository : UserAsyncRepository {
    private val userList = listOf(
        User(1L, "행맨"),
        User(2L, "올리"),
        User(3L, "에이스"),
        User(4L, "청명"),
        User(5L, "콜라")
    )

    private fun findUserByIdSync(userId: Long): User {
        return userList.first { it.id == userId }
    }

    override fun findUserByIdAsMono(userId: Long): Mono<User> {
        return Mono.just(findUserByIdSync(userId))
            .delayElement(Duration.ofMillis(TIME_DELAY_MS))
    }

    override fun findAllUserByIdsAsFlux(userIds: List<Long>): Flux<User> {
        val users = userIds.map { findUserByIdSync(it) }
        return Flux.fromIterable(users)
            .delayElements(Duration.ofMillis(TIME_DELAY_MS))
    }
}

class CoroutineUseCase(
    private val userRepository: UserRepository
) {
    suspend fun execute(userId: Long): User =
        userRepository.findUserByIdAsMono(userId).awaitSingle()

    suspend fun execute(userIds: List<Long>): List<User> =
        userRepository.findAllUserByIdsAsFlux(userIds).asFlow().toList()
}

/**
 * 코루틴과 오버로드를 사용하여 단일 유저와 배열 유저 조회 기능 생성
 * 코루틴을 실행시키기 위해서는 함수 앞에 suspend 가 붙어야한다.
 * - 관련 모든 함수에 suspend 를 붙일 수 없기 때문에 따로 클래스를 하나 만들어서 만드는 것이 좋아보인다. ex) fasade 패턴
 * Mono -> 0~1개의 결과를 반환, Flux -> 0~n개의 결과 반환
 * 함수 정의 시에 suspend 를 붙이고 싶지 않다면 runBlocking 안에서 코루틴 관련 로직 처리
 */
fun main() {
    val coroutine = CoroutineUseCase(UserRepository())

    runBlocking {
        val user = coroutine.execute(3L)
        println("user = $user")
        printLine()

        val userList = coroutine.execute(listOf(1, 2, 3, 4, 5))
        println("userList = $userList")
    }

}