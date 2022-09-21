package com.example.core.b_methodDefinitionAndCall

fun parsePath(path: String): Map<String, String> {
    val directory = path.substringBeforeLast("/")
    val fullName = path.substringAfterLast("/")
    val fileName = fullName.substringBeforeLast(".")
    val extension = fullName.substringAfterLast(".")
    return mapOf("directory" to directory, "fullName" to fullName, "fileName" to fileName, "extension" to extension)
}


fun main() {
    val path = "/user/ec2-user/app/StringParser.kt"
    val parseMap = parsePath(path)
    println("parseMap = $parseMap")
}
