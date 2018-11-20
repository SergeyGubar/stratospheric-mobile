package io.github.gubarsergey.stratosphericbaloon.entity


data class User(
    val role: String,
    val email: String,
    val password: String,
    val rememberMe: Boolean
)