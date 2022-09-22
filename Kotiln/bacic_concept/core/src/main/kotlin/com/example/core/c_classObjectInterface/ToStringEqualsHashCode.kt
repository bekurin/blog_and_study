package com.example.core.c_classObjectInterface

class Client(val name: String, val postalCode: Int) {
    override fun toString(): String =
            "Client(name=$name, postalCode=$postalCode)"

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Client)
            return false
        return name == other.name && postalCode == other.postalCode
    }

    override fun hashCode(): Int =
            name.hashCode() * 31 + postalCode
}

data class ClientNoToStringEqualsHashCode(val name: String, val postalCode: Int) {
}

fun main() {
    val client1 = Client("hangman", 1234)
    val client2 = Client("hangman", 1234)

//    val client1 = ClientNoToStringEqualsHashCode("hangman", 1234)
//    val client2 = ClientNoToStringEqualsHashCode("hangman", 1234)

    println(client1.toString())
    println(client1.equals(client2))

    val processed = hashSetOf(Client("hangman", 5678))
    println(processed.contains(Client("hangman", 5678)))

//    val processed = hashSetOf(ClientNoToStringEqualsHashCode("hangman", 5678))
//    println(processed.contains(ClientNoToStringEqualsHashCode("hangman", 5678)))
}