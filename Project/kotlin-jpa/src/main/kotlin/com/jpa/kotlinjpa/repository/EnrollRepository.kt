package com.jpa.kotlinjpa.repository

import com.jpa.kotlinjpa.entity.Enroll
import org.springframework.data.jpa.repository.JpaRepository

interface EnrollRepository: JpaRepository<Enroll, Long>
