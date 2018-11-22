package io.github.gubarsergey.stratosphericbaloon.photo

import io.github.gubarsergey.stratosphericbaloon.constant.IMAGE_BASE_URL
import io.github.gubarsergey.stratosphericbaloon.retrofit.api.photo.PhotosApi
import io.reactivex.Observable
import retrofit2.Retrofit

class PhotosRepository(val retrofit: Retrofit) {

    fun getUserPhotos(token: String): Observable<List<String>> {
        return retrofit.create(PhotosApi::class.java)
            .getPhotos(token)
            .flatMap { list ->
                Observable.fromIterable(list)
                    .map { "$IMAGE_BASE_URL$it" }
                    .toList()
                    .toObservable()
            }

    }
}