package io.github.gubarsergey.stratosphericbaloon.retrofit.api.register

import io.github.gubarsergey.stratosphericbaloon.retrofit.model.TokenResponseModel
import io.github.gubarsergey.stratosphericbaloon.retrofit.model.UserLoginModel
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("user/login")
    fun login(@Body loginModel: UserLoginModel): Observable<TokenResponseModel>
}