package io.github.gubarsergey.stratosphericbaloon.retrofit.api.register

import io.github.gubarsergey.stratosphericbaloon.retrofit.model.RegisterResponseModel
import io.github.gubarsergey.stratosphericbaloon.retrofit.model.UserRegisterModel
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApi {
    @POST("user/register")
    fun register(@Body user: UserRegisterModel): Observable<RegisterResponseModel>
}