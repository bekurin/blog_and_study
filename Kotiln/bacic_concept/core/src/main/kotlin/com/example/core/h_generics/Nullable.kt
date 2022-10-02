package com.example.core.h_generics

import com.example.core.a_kotlinBasic.iterator.printLine

class Processor1<T: Any> {
    fun process(value: T): Int =
            value.hashCode()
}

class Processor2<T: Any?> {
    fun process(value: T): Int? =
            value?.hashCode()
}

/**
 * 타입 파라미터를 널이 될 수 없는 타입으로 한정할 수 있다.
 * processor1 의 경우 널이 될 수 없는 타입만을 process 의 value 에 입력할 수 있다.
 * processor2 의 경우 널이 될 수 있는 타입을 입력 받기 때문에 함수를 호출할 때 안전한 호출을 사용해야 한다.
 */
fun main() {
    val nullableStringProcessor = Processor2<String?>()
    println("nullableStringProcessor.process(null) = ${nullableStringProcessor.process(null)}")
    printLine()

    val stringProcessor = Processor1<String>()
    println("stringProcessor.process(\"hello world\") = ${stringProcessor.process("hello world")}")
}