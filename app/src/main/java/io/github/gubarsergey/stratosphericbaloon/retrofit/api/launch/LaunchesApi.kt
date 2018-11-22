package io.github.gubarsergey.stratosphericbaloon.retrofit.api.launch

import io.github.gubarsergey.stratosphericbaloon.entity.Launch
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header

interface LaunchesApi {
    @GET("values/launches")
    fun getLaunches(@Header("Authorization") token: String): Observable<List<Launch>>
}