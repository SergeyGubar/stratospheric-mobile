package io.github.gubarsergey.stratosphericbaloon.db.launches

import android.content.Context
import io.github.gubarsergey.stratosphericbaloon.db.launches.enitity.Launch
import io.github.gubarsergey.stratosphericbaloon.helper.SharedPrefHelper
import io.github.gubarsergey.stratosphericbaloon.api.launch.LaunchesApi
import io.reactivex.Observable
import retrofit2.Retrofit

class LaunchesRepository(
    val context: Context,
    val retrofit: Retrofit
) {
    fun getAllLaunches(): Observable<List<Launch>> {
        return retrofit.create(LaunchesApi::class.java)
            .getLaunches("Bearer ${SharedPrefHelper.getToken(context)}")
    }

    fun getNearLaunches(longitude: Double, latitude: Double): Observable<List<Launch>> {
        return retrofit.create(LaunchesApi::class.java)
            .getLaunchesWithinRadius("Bearer ${SharedPrefHelper.getToken(context)}", longitude, latitude)
    }
}