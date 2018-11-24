package io.github.gubarsergey.stratosphericbaloon.api.launch

import io.github.gubarsergey.stratosphericbaloon.api.launch.entitiy.AddLaunchModel
import io.github.gubarsergey.stratosphericbaloon.db.launches.enitity.Launch
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.*

interface LaunchesApi {

    @GET("values/launches")
    fun getLaunches(@Header("Authorization") token: String): Observable<List<Launch>>

    @GET("user/launchesRadius")
    fun getLaunchesWithinRadius(
        @Header("Authorization") token: String,
        @Query("longitude") longitude: Double,
        @Query("latitude") latitude: Double
    ): Observable<List<Launch>>

    @DELETE("user/removeLaunch")
    fun removeLaunch(@Header("Authorization") token: String,
                     @Query("id") id: String): Observable<ResponseBody>

    @POST("user/addLaunch")
    fun addLaunch(@Header("Authorization") token: String,
                  @Body model: AddLaunchModel): Observable<ResponseBody>
}