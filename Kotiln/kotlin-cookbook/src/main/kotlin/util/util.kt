package util

fun printLine(size: Int = 30) {
    val line =  List(size) {"-"}.joinToString(separator = "")
    println(line)
}