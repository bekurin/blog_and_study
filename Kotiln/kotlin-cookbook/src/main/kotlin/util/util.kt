package util

fun printLine(size: Int = 30) {
    val line = List(size) { "-" }.joinToString(separator = "")
    println(line)
}

fun <T> Array<T>.printArray(separator: String = ", "): String {
    var result = ""
    this.forEachIndexed { index, it ->
        result +=
            if (index == (this.size - 1)) it.toString()
            else (it.toString() + separator)
    }
    return result
}

fun <T> List<T>.printList(separator: String = ", "): String {
    var result = ""
    this.forEachIndexed { index, it ->
        result +=
            if (index == (this.size - 1)) it.toString()
            else (it.toString() + separator)
    }
    return result
}