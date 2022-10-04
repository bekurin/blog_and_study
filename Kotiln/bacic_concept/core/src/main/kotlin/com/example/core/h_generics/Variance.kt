package com.example.core.h_generics

import com.example.core.a_kotlinBasic.iterator.printLine

open class Animal

class Cat: Animal()

interface Feed<T> {
    fun feed()
}

fun animalFeed(element: Feed<Animal>) {
    element.feed()
}

fun animalFeedOut(element: Feed<out Animal>) {
    element.feed()
}

fun animalFeedIn(element: Feed<in Animal>) {
    element.feed()
}

/**
 * 무공변성 자신의 타입을 제외한 타입을 허용하지 않는 것
 * 공변성 자기 자신과 자식 객체를 허용하는 것
 * 반공변성 자기 자신과 부모 객체만 허용하는 것
 */
fun main() {
    val animal = object : Feed<Animal> {
        override fun feed() {
            println("animal feeding!")
        }
    }
    val cat = object : Feed<Cat> {
        override fun feed() {
            println("cat feeding!")
        }
    }
    val any = object : Feed<Any> {
        override fun feed() {
            println("any feeding!")
        }
    }

    val nothing = object : Feed<Nothing> {
        override fun feed() {
            println("nothing feeding!")
        }
    }

    // 무공변성
    // println("animalFeed(any) = ${animalFeed(any)}") Type mismatch
    println("animalFeed(animal) = ${animalFeed(animal)}")
    // println("animalFeed(cat) = ${animalFeed(cat)}") Type mismatch
    // println("animalFeed(nothing) = ${animalFeed(nothing)}") Type mismatch
    printLine()

    // 공변성
    // println("animalFeedOut(any) = ${animalFeedOut(any)}") Type mismatch
    println("animalFeedOut(animal) = ${animalFeedOut(animal)}")
    println("animalFeedOut(cat) = ${animalFeedOut(cat)}")
    println("animalFeedOut(nothing) = ${animalFeedOut(nothing)}")
    printLine()

    // 반공변성
    println("animalFeedIn(any) = ${animalFeedIn(any)}")
    println("animalFeedIn(animal) = ${animalFeedIn(animal)}")
    // println("animalFeedIn(cat) = ${animalFeedIn(cat)}") Type mismatch
    // println("animalFeedIn(nothing) = ${animalFeedIn(nothing)}") Type mismatch
}
