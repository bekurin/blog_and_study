package com.example.core.d_lambdaProgramming

fun printMessageWithPrefix(messages: Collection<String>, prefix: String) {
    messages.forEach {
        println("$prefix $it")

    }
}

fun printProblemCounts(responses: Collection<String>) {
    var clientErrors = 0
    var serverErrors = 0

    responses.forEach {
        if (it.startsWith("4")) {
            clientErrors += 1
        } else if (it.startsWith("5")) {
            serverErrors += 1
        }
    }
    println("$clientErrors client errors, $serverErrors server errors.")
}

fun main() {
    val errorList = listOf("403 Forbidden", "404 Not Found")
    printMessageWithPrefix(errorList, prefix = "Error: ")

    val responseList = listOf("200 Ok", "418 I'm a teapot", "500 Internal Server Error")
    printProblemCounts(responseList)
    printProblemCounts(errorList)

}