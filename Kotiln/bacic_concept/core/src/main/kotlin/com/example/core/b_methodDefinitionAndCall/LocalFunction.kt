package com.example.core.b_methodDefinitionAndCall

class User(
        val id: Int,
        val name: String = "",
        val address: String = ""
)

fun saveUser(user: User) {
    if (user.name.isEmpty()) {
        throw IllegalStateException(
                "Can't save user ${user.id}: empty name"
        )
    }

    if (user.address.isEmpty()) {
        throw IllegalStateException(
                "Can't save user ${user.id}: empty address"
        )
    }

    println("user save!")
}

fun saveUserLocalFun(user: User) {
    fun validate(user: User,
                 value: String,
                 fieldName: String) {
        if (value.isEmpty()) {
            throw IllegalStateException(
                    "Can't save user ${user.id}: empty $fieldName"
            )
        }
    }

    validate(user, user.name, "Name")
    validate(user, user.address, "Address")
    println("user save!")
}

fun main() {
    val user = User(3, "hangman", "seoul")
    val userWithoutName = User(3, address = "seoul")
    val userWithoutAddress = User(3, "hangman")

    saveUser(user)
//    saveUser(userWithoutName)
//    saveUser(userWithoutAddress)


    saveUserLocalFun(user)
//    saveUserLocalFun(userWithoutName)
//    saveUserLocalFun(userWithoutAddress)
}