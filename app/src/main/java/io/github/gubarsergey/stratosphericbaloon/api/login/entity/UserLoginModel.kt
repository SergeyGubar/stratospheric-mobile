package io.github.gubarsergey.stratosphericbaloon.api.login.entity

data class UserLoginModel(
    val email: String,
    val password: String,
    val rememberMe: Boolean = true
)