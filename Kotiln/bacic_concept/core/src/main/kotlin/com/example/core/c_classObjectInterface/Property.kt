package com.example.core.c_classObjectInterface

interface User {
    val nickname: String
}

class PrivateUser(override val nickname: String) : User

class SubscribingUser(val email: String) : User {
    override val nickname: String
        get() = email.substringBeforeLast("@")
}

class Member(val name: String) {
    var address: String = "unknown"
        set(value: String) {
            println("""
                Address was changed for $name:
                "$field" -> "$value".""".trimIndent())
            field = value
        }
}

fun main() {
    val email = "hangman@socar.kr"
    println(PrivateUser(email).nickname)
    println(SubscribingUser(email).nickname)

    println("------------------------------")

    val member = Member("hangman")
    member.address = "seoul"
    println(member.address)

    member.address = "busan"
    println(member.address)
}