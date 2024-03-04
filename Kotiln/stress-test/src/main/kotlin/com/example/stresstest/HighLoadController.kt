package com.example.stresstest

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.random.Random

@RestController
class HighLoadController {
    @GetMapping("/high-load-cpu")
    fun highLoadCpu(): Long {
        return (1..20_000_000).sumOf {
            Random.nextLong(Long.MAX_VALUE)
        }
    }

    @GetMapping("/high-load-memory")
    fun highLoadMemory(): Int {
        val mutableListOf = mutableListOf<Int>()
        repeat(500_000) {
            mutableListOf.add(it)
        }
        return mutableListOf.size
    }
}
