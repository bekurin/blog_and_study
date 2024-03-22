package com.example.pipeline

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PipelineApplication

fun main(args: Array<String>) {
	runApplication<PipelineApplication>(*args)
}
