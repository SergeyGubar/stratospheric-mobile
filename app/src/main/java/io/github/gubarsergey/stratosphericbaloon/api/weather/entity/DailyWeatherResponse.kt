package io.github.gubarsergey.stratosphericbaloon.api.weather.entity

import io.github.gubarsergey.stratosphericbaloon.entity.DailyWeatherData

data class DailyWeatherResponse(
    val summary: String,
    val icon: String,
    val data: List<DailyWeatherData>
)