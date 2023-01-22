package com.example.logic.authentication.model

class User(
    val username: String,
    val password: String? = null,
    val code: String? = null
) {
}