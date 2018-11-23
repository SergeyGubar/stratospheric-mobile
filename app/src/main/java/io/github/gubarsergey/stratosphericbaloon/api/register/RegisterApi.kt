package io.github.gubarsergey.stratosphericbaloon.api.register

import io.github.gubarsergey.stratosphericbaloon.api.login.entity.TokenResponseModel
import io.github.gubarsergey.stratosphericbaloon.api.register.entity.UserRegisterModel
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApi {
    @POST("user/register")
    fun register(@Body user: UserRegisterModel): Observable<TokenResponseModel>
}