package com.example.core.c_classObjectInterface

class CountingSet<T>(
        val innerSet: MutableCollection<T> = HashSet<T>()
): MutableCollection<T> by innerSet {
    var objectsAdded = 0
    override fun add(element: T): Boolean {
        objectsAdded++
        return innerSet.add(element)
    }

    override fun addAll(elements: Collection<T>): Boolean {
        objectsAdded += elements.size
        return innerSet.addAll(elements)
    }
}


fun main() {
    val cset = CountingSet<Int>()
    cset.addAll(listOf(1,2,3))
    println("${cset.objectsAdded} objects were added, ${cset.size} remain")

    cset.remove(1)
    println("cset = ${cset.size}")
}