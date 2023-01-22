package com.example.chapter11.repository

import com.example.authentication.entity.Otp
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface OtpRepository: JpaRepository<Otp, String> {
    fun findOtpByUsername(username: String): Optional<Otp>
}