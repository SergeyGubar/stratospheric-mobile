package io.github.gubarsergey.stratosphericbaloon.retrofit.model

data class UserRegisterModel(
    val role: String,
    val email: String,
    val password: String,
    val rememberMe: Boolean
)