package io.github.gubarsergey.stratosphericbaloon.db.weather

import android.content.Context
import io.github.gubarsergey.stratosphericbaloon.api.weather.WeatherApi
import io.github.gubarsergey.stratosphericbaloon.helper.SharedPrefHelper
import io.github.gubarsergey.stratosphericbaloon.api.weather.WeatherService
import io.github.gubarsergey.stratosphericbaloon.api.weather.entity.DailyWeatherResponse
import io.github.gubarsergey.stratosphericbaloon.api.weather.entity.WeatherResponse
import io.github.gubarsergey.stratosphericbaloon.entity.DailyWeatherData
import io.reactivex.Observable
import retrofit2.Retrofit

/*
class WeatherRepository(val service: WeatherService,
                        val storage: WeatherStorage) {
    fun getWeather(longitude: Double, latitude: Double): Observable<WeatherResponse> {
        return service.getWeatherApi()
            .getWeather("Bearer ${SharedPrefHelper.getToken(context)}", longitude, latitude)
    }
}*/

class WeatherRepository(
    val context: Context,
    val retrofit: Retrofit
) {
    fun getWeather(longitude: Double, latitude: Double): Observable<DailyWeatherData> {
        return retrofit.create(WeatherApi::class.java)
            .getWeather("Bearer ${SharedPrefHelper.getToken(context)}", longitude, latitude)
            .map { it.daily.data.first() }
    }
}
