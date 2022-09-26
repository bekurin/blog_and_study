package com.example.core.e_kotlinTypeSystem

fun sendEmailTo(email: String) {
    println("email = $email")
}

/**
 * 안전한 호출 .? 와 함께 let 함수를 사용하면 널이 아닌 값만 인자로 받는 함수 처리를 간단하게 할 수 있다.

 */
fun main() {
    var email: String? = "test@email.com"
    email?.let { sendEmailTo(it) }

    email = null
    email?.let { sendEmailTo(it) }
}