package com.example.core.c_classObjectInterface

class CompanionUser private constructor(val nickname: String) {
    companion object {
        fun newSubscribingUser(email: String) =
                CompanionUser(email.substringBeforeLast("@"))

        fun newSNSUser(accountId: Int) =
                CompanionUser(accountId.toString())
    }
}

fun main() {
    val email = "hangman@socar.kr"
    val emailUser = CompanionUser.newSubscribingUser(email)
    val snsUser = CompanionUser.newSNSUser(112)

    println("emailUser.nickname = ${emailUser.nickname}")
    println("snsUser.nickname = ${snsUser.nickname}")
}