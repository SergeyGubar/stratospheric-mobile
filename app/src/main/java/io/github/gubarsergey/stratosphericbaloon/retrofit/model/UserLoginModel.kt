package io.github.gubarsergey.stratosphericbaloon.retrofit.model

data class UserLoginModel(
    val email: String,
    val password: String,
    val rememberMe: Boolean = true
)