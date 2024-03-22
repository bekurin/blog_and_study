package com.example.countingdailyvisitors.domain

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long>
