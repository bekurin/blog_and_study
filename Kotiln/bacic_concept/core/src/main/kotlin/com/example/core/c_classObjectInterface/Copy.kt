package com.example.core.c_classObjectInterface

class Copy(val name: String, val postalCode: Int) {
    fun copy(name: String = this.name,
             postalCode: Int = this.postalCode) = 
            Client(name, postalCode)
}


fun main() {
    val copy = Copy("init name", 123456)
    println("temp.copy() = ${copy.copy(name = "changed name")}")
}