package io.github.gubarsergey.stratosphericbaloon.entity

data class DailyWeatherData(
    val summary: String,
    val temperatureHigh: Double,
    val temperatureLow: Double,
    val windSpeed: Double,
    val humidity: Double
)