package io.github.gubarsergey.stratosphericbaloon.retrofit.model

import com.google.gson.annotations.SerializedName

data class RegisterResponseModel(
    @SerializedName("access_token" ) val token: String,
    val userName: String
)