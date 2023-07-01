package com.jpa.kotlinjpa.repository

import com.jpa.kotlinjpa.entity.Student
import org.springframework.data.jpa.repository.JpaRepository

interface StudentRepository: JpaRepository<Student, Long>
