package com.example.chapter18.controller

import com.example.chapter18.entity.Workout
import com.example.chapter18.service.WorkoutService
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/workout")
class WorkoutController(
    private val workoutService: WorkoutService,
) {
    @PostMapping
    fun add(
        @RequestBody workout: Workout,
    ) {
        workoutService.saveWorkout(workout)
    }

    @GetMapping
    fun findAll(auth: Authentication): List<Workout> {
        println("auth.authorities = ${auth.authorities}")
        return workoutService.findWorkouts()
    }

    @DeleteMapping("/{id}")
    fun delte(
        @PathVariable id: Long,
    ): Long {
        return workoutService.deleteWorkout(id)
    }
}