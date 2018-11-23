package io.github.gubarsergey.stratosphericbaloon.api.weather.entity

data class WeatherResponse(
    val latitude: Double,
    val longitude: Double,
    val timeZone: String,
    val daily: DailyWeatherResponse
)