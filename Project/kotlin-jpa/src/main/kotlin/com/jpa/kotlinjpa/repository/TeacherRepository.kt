package com.jpa.kotlinjpa.repository

import com.jpa.kotlinjpa.entity.Teacher
import org.springframework.data.jpa.repository.JpaRepository

interface TeacherRepository: JpaRepository<Teacher, Long>
