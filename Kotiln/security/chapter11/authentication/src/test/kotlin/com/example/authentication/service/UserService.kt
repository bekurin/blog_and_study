package com.example.chapter11.service

import com.example.authentication.entity.Otp
import com.example.authentication.entity.Users
import com.example.authentication.repository.OtpRepository
import com.example.authentication.repository.UserRepository
import com.example.authentication.util.GenerateCodeUtil
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(
    private val passwordEncoder: PasswordEncoder,
    private val userRepository: UserRepository,
    private val otpRepository: OtpRepository,
) {

    @Transactional
    fun addUser(users: Users) {
        users.password = passwordEncoder.encode(users.password)
        userRepository.save(users)
    }

    @Transactional
    fun auth(users: Users) {
        val findUser = userRepository.findUserByUsername(users.username)
            .orElseThrow { BadCredentialsException("Bad credentials") }
        if (!passwordEncoder.matches(users.password, findUser.password)) {
            throw BadCredentialsException("Bad credentials")
        }
        renewOtp(findUser)
    }

    fun check(otpToValidate: Otp): Boolean {
        val userOtp = otpRepository.findOtpByUsername(otpToValidate.username)
        if (userOtp.isPresent) {
            val otp = userOtp.get()
            if (otpToValidate.code == otp.code) {
                return true
            }
        }
        return false
    }

    private fun renewOtp(findUsers: Users) {
        val code = GenerateCodeUtil.generateCode()
        println("code = ${code}")
        val userOtp = otpRepository.findOtpByUsername(findUsers.username)
        if (userOtp.isPresent) {
            val otp = userOtp.get()
            otp.code = code
        } else {
            val otp = Otp()
            otp.username = findUsers.username
            otp.code = code
            otpRepository.save(otp)
        }
    }
}