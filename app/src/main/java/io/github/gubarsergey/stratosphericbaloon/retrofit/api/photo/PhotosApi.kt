package io.github.gubarsergey.stratosphericbaloon.retrofit.api.photo

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header

interface PhotosApi {
    @GET("user/getPhotos")
    fun getPhotos(@Header("Authorization") token: String): Observable<List<String>>
}