package io.github.gubarsergey.stratosphericbaloon.retrofit.api.launch

import io.github.gubarsergey.stratosphericbaloon.entity.Launch
import io.reactivex.Observable
import retrofit2.http.GET

interface LaunchesApi {

    @GET("values/launches")
    fun getLaunches(): Observable<List<Launch>>

    @GET
    fun getLaunchesInRadius()
}