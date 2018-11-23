package io.github.gubarsergey.stratosphericbaloon.api.login

import io.github.gubarsergey.stratosphericbaloon.api.login.entity.TokenResponseModel
import io.github.gubarsergey.stratosphericbaloon.api.login.entity.UserLoginModel
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("user/login")
    fun login(@Body loginModel: UserLoginModel): Observable<TokenResponseModel>
}