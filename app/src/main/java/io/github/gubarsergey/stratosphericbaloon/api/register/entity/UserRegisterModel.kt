package io.github.gubarsergey.stratosphericbaloon.api.register.entity

data class UserRegisterModel(
    val email: String,
    val password: String,
    val rememberMe: Boolean,
    val role: String
)