package io.github.gubarsergey.stratosphericbaloon.api.photo

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface PhotosApi {
    @GET("user/getPhotos")
    fun getPhotos(@Header("Authorization") token: String): Observable<List<String>>

    @DELETE("user/removePhoto")
    fun removePhoto(@Header("Authorization") token: String,
                    @Query("id") id: String): Observable<ResponseBody>
}