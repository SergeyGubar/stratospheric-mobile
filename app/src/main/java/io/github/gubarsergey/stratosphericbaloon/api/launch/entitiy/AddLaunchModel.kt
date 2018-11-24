package io.github.gubarsergey.stratosphericbaloon.api.launch.entitiy

data class AddLaunchModel(
    val name: String,
    val time: String,
    val longitude: Double,
    val latitude: Double
)