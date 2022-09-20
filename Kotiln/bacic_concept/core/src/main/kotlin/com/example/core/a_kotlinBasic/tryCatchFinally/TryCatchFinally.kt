package com.example.core.a_kotlinBasic.tryCatchFinally

import java.io.BufferedReader
import java.io.StringReader

fun readNumber(reader: BufferedReader): Int? {
    try {
        val line = reader.readLine()
        return Integer.parseInt(line)
    } catch (e: NumberFormatException) {
        return null
    } finally {
        reader.close()
    }
}

fun readNumberLikeKotlin(reader: BufferedReader): Int? {
    val number = try {
        Integer.parseInt(reader.readLine())
    } catch (e: NumberFormatException) {
        null
    }
    return number
}

fun main(){
    val reader = BufferedReader(StringReader("2323232"))
    println(readNumber(reader))
}