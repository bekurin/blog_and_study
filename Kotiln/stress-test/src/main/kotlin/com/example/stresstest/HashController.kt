package com.example.stresstest

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.concurrent.ConcurrentHashMap


@RestController
class HashController {
    val cacheHashResult = ConcurrentHashMap<String, String>()

    @GetMapping("/no-cache-hash-string")
    fun noCacheHashString(
        @RequestParam input: String
    ): String {
        return calculateHash(input)
    }

    @GetMapping("/cached-hash-string")
    fun cachedHashString(
        @RequestParam input: String
    ): String? {
        if (cacheHashResult.containsKey(input)) {
            return cacheHashResult[input]
        }
        val hashedResult = calculateHash(input)
        cacheHashResult[input] = hashedResult
        return hashedResult
    }

    private fun calculateHash(input: String): String {
        var hash = input
        try {
            val md = MessageDigest.getInstance("SHA-256")
            repeat(50000) {
                val bytes = hash.toByteArray()
                val hashedBytes = md.digest(bytes)
                hash = bytesToHex(hashedBytes)
                md.reset()
            }
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return hash
    }

    private fun bytesToHex(bytes: ByteArray): String {
        return bytes.joinToString("") { "%02x".format(it) }
    }
}
