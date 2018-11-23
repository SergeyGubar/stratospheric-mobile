package io.github.gubarsergey.stratosphericbaloon.retrofit.api.launch

import io.github.gubarsergey.stratosphericbaloon.entity.Launch
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface LaunchesApi {
    @GET("values/launches")
    fun getLaunches(@Header("Authorization") token: String): Observable<List<Launch>>

    @GET("user/launchesRadius")
    fun getLaunchesWithinRadius(
        @Header("Authorization") token: String,
        @Query("longitude") longitude: Double,
        @Query("latitude") latitude: Double
    ): Observable<List<Launch>>

}