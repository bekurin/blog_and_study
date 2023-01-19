package com.example.exercise01

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Exercise01Application

/**
 * 이전에 jwt를 사용하여 로그인을 구현했을 때는 다음과 같은 코드를 사용하여 인증을 가져왔는데
 * Authentication auth = SecurityContextHolder.getContext().getAuthentication();
 * 현재에는 함수의 인자에 auth: Authentication을 사용하면 현재 인증된 security context의 값이 저장된다.
 *
 * Spring Security를 사용하면 각 쓰레드가 보안 컨텍스트에 대한 내용을 저장해야한다.
 * - MODE_THREADLOCAL: 각 스레드가 보안 컨텍스트를 가지고 있다.
 * - MODE_INHERITABLETHREADLOCAL: async를 사용하여 새로운 스레드가 생기면 기존 스레드의 보안 컨텍스트를 복사한다.
 * - MODE_GLOBAL: 모든 스레드가 같은 보안 컨텍스트를 가진다.
 * MODE_GLOBAL의 경우 보안에 위험이 있을 것 같다.
 * 프레임워크에서 모르는 스레드를 개발자가 생성하면 프레임워크는 모르기 때문에 MODE_INHERITABLETHREADLOCAL 사용에 문제가 있을 수 있다.
 *
 * AuthenticationProvider => 카드, 열쇠, 비밀번호 등 여러 인증할 방식
 * Authentication => 인증이 승인되면 인가 정보를 저장한다.
 * AuthenticationManager => 자물쇠, 도어락과 같은 인증 승인 여부를 결정하는 주체
 */
fun main(args: Array<String>) {
	runApplication<Exercise01Application>(*args)
}
