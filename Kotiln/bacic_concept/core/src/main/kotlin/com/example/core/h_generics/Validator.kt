package com.example.core.h_generics

import kotlin.reflect.KClass

interface FieldValidator<in T> {
    fun validate(input: T): Boolean
}

object DefaultStringValidator: FieldValidator<String> {
    override fun validate(input: String) =
            input.isEmpty()
}

object DefaultIntValidator: FieldValidator<Int> {
    override fun validate(input: Int) =
            input >= 0
}

object Validators {
    private val validators = mutableMapOf<KClass<*>, FieldValidator<*>>()

    fun <T: Any> registerValidator(
            KClass: KClass<T>, fieldValidator: FieldValidator<T>
    ) {
        validators[KClass] = fieldValidator
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <T: Any> get(kClass: KClass<T>): FieldValidator<T> =
            validators[kClass] as? FieldValidator<T>
                    ?: throw IllegalArgumentException("No validator for ${kClass.simpleName}")
}

/**
 * 스타프로젝션은 타입을 가져올 수 없다. 따라서 validators 에 FieldValidator 를 저장해도 정확한 타입을 알 수 없기 때문에 사용할 수 없다.
 * 정확한 타입이 필요하다면 Validators 의 구현 처럼 validators 를 private 으로 숨기고 저장 및 조회 기능을 추가한다.
 * 이때, 조회 기능에는 안전한 타입 캐스팅과 엘비스 연산자로 null 값에 대응한다.
 */
fun main() {
    val validators = mutableMapOf<KClass<*>, FieldValidator<*>>()
    validators[String::class] = DefaultStringValidator
    validators[Int::class] = DefaultIntValidator

    // println("validators[String::class]!!.validate(\"\") = ${validators[String::class]!!.validate("")}")
    // * 타입을 원하기 때문에 String 을 검증하기에 안전하지 않다고 판단
    Validators.registerValidator(String::class, DefaultStringValidator)
    Validators.registerValidator(Int::class, DefaultIntValidator)
    println("Validators[String::class].validate(\"kotlin\") = ${Validators[String::class].validate("kotlin")}")
    println("Validators[Int::class].validate(3487) = ${Validators[Int::class].validate(3487)}")
}