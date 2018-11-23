package io.github.gubarsergey.stratosphericbaloon.api.login.entity

import com.google.gson.annotations.SerializedName

data class TokenResponseModel(
    @SerializedName("access_token" ) val token: String,
    val userName: String
)