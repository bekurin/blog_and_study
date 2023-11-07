package com.jpa.kotlinjpa.controller

import com.jpa.kotlinjpa.repository.StudentRepository
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Transactional(readOnly = true)
class CollectionVsSequenceController(
        private val studentRepository: StudentRepository
) {

    @GetMapping("/v1/collection")
    fun collection(): List<Long> {
        val currentTimeMillis = System.currentTimeMillis()
        val longList = studentRepository.findAll()
                .flatMap { student ->
                    student.enrolls
                }
                .map { it.getId() }
        println("${System.currentTimeMillis() - currentTimeMillis}ms")
        return longList
    }

    @GetMapping("/v1/sequence")
    fun sequence(): List<Long> {
        val currentTimeMillis = System.currentTimeMillis()
        val longList = studentRepository.findAll()
                .asSequence()
                .flatMap { student ->
                    student.enrolls
                }
                .map { it.getId() }
                .toList()
        println("${System.currentTimeMillis() - currentTimeMillis}ms")
        return longList
    }
}
