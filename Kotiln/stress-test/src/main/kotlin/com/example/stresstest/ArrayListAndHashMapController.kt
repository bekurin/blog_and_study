package com.example.stresstest

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
class ArrayListAndHashMapController {

    private val N = 10000000
    private val arrayList: MutableList<Int> = ArrayList()
    private val hashMap: MutableMap<Int, String> = HashMap()

    init {
        repeat(N) {
            arrayList.add(it)
            hashMap[it] = "Value$it"
        }
    }

    @GetMapping("/arraylist")
    fun arrayListPerformance(@RequestParam target: Int): String? {
        return timestamp("ArrayList") { arrayList.firstOrNull { it == target } }
    }

    @GetMapping("/hashmap")
    fun hashMapPerformance(@RequestParam target: Int): String {
        return timestamp("HashMap") { hashMap[target] }
    }

    private fun timestamp(caller: String, action: () -> Any?): String {
        val startTime = System.currentTimeMillis()
        val result = action()
        val endTime = System.currentTimeMillis()
        val elapsedTime = endTime - startTime
        return "$caller Lookup Time: $elapsedTime ms, Founded: $result"
    }
}
