package io.github.gubarsergey.stratosphericbaloon.db.photo

import android.content.Context
import io.github.gubarsergey.stratosphericbaloon.api.IMAGE_BASE_URL
import io.github.gubarsergey.stratosphericbaloon.api.photo.PhotosApi
import io.github.gubarsergey.stratosphericbaloon.helper.SharedPrefHelper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Retrofit

class PhotosRepository(
    val context: Context,
    val retrofit: Retrofit
) {

    fun getUserPhotos(token: String): Observable<List<Pair<String, String>>> {
        return retrofit.create(PhotosApi::class.java)
            .getPhotos(token)
            .flatMap { list ->
                Observable.fromIterable(list)
                    .map { "$IMAGE_BASE_URL$it" to it }
                    .toList()
                    .toObservable()
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    fun removePhoto(id: String): Observable<ResponseBody> {
        return retrofit.create(PhotosApi::class.java)
            .removePhoto("Bearer ${SharedPrefHelper.getToken(context)}" , id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}