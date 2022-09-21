package com.example.core.b_methodDefinitionAndCall

class Person(val id: Int, val name: String = "", val address: String = "")

fun savePerson(person: Person) {
    fun validate(value: String, fieldName: String) {
        if (value.isEmpty()) {
            throw IllegalStateException("Can't save user ${person.id}: empty $fieldName")
        }
    }

    validate(person.name, "Name")
    validate(person.address, "Address")
    println("user save!")
}

fun Person.validateBeforeSave() {
    fun validate(value: String, fieldName: String) {
        if (value.isEmpty()) {
            throw IllegalStateException("Can't save user $id: empty $fieldName")
        }
    }
    validate(name, "Name")
    validate(address, "Address")
    println("save Person!")
}

fun savePersonAdvanced(person: Person) {
    person.validateBeforeSave()
    println("save Person!")
}

fun main() {
    val person = Person(3, "hangman", "seoul")
    val personWithoutName = Person(3, address = "seoul")
    val personWithoutAddress = Person(3, "hangman")

    savePerson(person)
//    savePerson(personWithoutName)
//    savePerson(personWithoutAddress)

    savePersonAdvanced(person)
//    savePersonAdvanced(personWithoutName)
//    savePersonAdvanced(personWithoutAddress)
}