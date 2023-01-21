package com.example.chapter11.util

import java.security.NoSuchAlgorithmException
import java.security.SecureRandom

class GenerateCodeUtil {
    companion object {
        fun generateCode(): String {
            var code = ""
            try {
                val random = SecureRandom.getInstanceStrong()
                code = (random.nextInt(900000) + 100000).toString()
            } catch (e: NoSuchAlgorithmException) {
                throw RuntimeException("Problem when generating the random code.");
            }
            return code
        }
    }
}