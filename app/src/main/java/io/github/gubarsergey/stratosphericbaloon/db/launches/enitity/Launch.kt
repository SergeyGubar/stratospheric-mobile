package io.github.gubarsergey.stratosphericbaloon.db.launches.enitity

data class Launch(
    val id: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val time: String
)