package com.example.core.l_tip

import com.example.core.a_kotlinBasic.iterator.printLine

fun kakaoLogin() {
    println("kakao 로그인 중!")
}

fun googleLogin() {
    println("google 로그인 중!")
}

fun googleAfterLogin() {
    println("google 로그인 후 처리 작업 실행!")
}

fun naverLogin() {
    println("naver 로그인 중!")
}

fun originLogin(type: String) {
    if (type == "KAKAO_LOGIN") {
        kakaoLogin()
    } else if (type == "GOOGLE_LOGIN") {
        googleLogin()
        googleAfterLogin()
    } else if (type == "NAVER_LOGIN") {
        naverLogin()
    }
}

enum class Type {KAKAO_LOGIN, GOOGLE_LOGIN, NAVER_LOGIN}

fun enumLogin(type: Type) {
    when(type) {
        Type.KAKAO_LOGIN -> kakaoLogin()
        Type.GOOGLE_LOGIN -> {
            googleLogin()
            googleAfterLogin()
        }
        Type.NAVER_LOGIN -> naverLogin()
    }
}

fun main() {
    originLogin("GOOGLE_LOGIN")
    printLine()
    enumLogin(Type.GOOGLE_LOGIN)
}