package io.github.gubarsergey.stratosphericbaloon.retrofit.model

data class UserRegisterModel(
    val email: String,
    val password: String,
    val rememberMe: Boolean,
    val role: String
)