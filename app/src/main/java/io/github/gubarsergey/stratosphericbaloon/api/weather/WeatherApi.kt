package io.github.gubarsergey.stratosphericbaloon.api.weather

import io.github.gubarsergey.stratosphericbaloon.api.weather.entity.WeatherResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface WeatherApi {
    @GET("values/weather")
    fun getWeather(
        @Header("Authorization") token: String,
        @Query("longitude") longitude: Double,
        @Query("latitude") latitude: Double
    ): Observable<WeatherResponse>
}