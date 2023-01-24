package com.example.chapter18.service

import com.example.chapter18.entity.Workout
import com.example.chapter18.repository.WorkoutRepository
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class WorkoutService(
    private val workoutRepository: WorkoutRepository,
) {

    @Transactional
    @PreAuthorize("#workout.username == authentication.name")
    fun saveWorkout(workout: Workout) {
        workoutRepository.save(workout)
    }

    fun findWorkouts(): List<Workout> {
        return workoutRepository.findAllByUser()
    }

    @Transactional
    fun deleteWorkout(id: Long): Long {
        workoutRepository.deleteById(id)
        return id
    }

}