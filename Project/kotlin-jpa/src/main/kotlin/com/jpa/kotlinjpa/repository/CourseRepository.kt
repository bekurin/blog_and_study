package com.jpa.kotlinjpa.repository

import com.jpa.kotlinjpa.entity.Course
import org.springframework.data.jpa.repository.JpaRepository

interface CourseRepository: JpaRepository<Course, Long>
