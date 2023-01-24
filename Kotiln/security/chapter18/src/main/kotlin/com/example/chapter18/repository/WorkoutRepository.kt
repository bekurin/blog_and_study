package com.example.chapter18.repository

import com.example.chapter18.entity.Workout
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface WorkoutRepository : JpaRepository<Workout, Long> {
    @Query("select w from Workout w where w.username = ?#{authentication.name}")
    fun findAllByUser(): List<Workout>
}