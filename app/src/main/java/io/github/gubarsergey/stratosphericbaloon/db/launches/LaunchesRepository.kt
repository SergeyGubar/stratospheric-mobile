package io.github.gubarsergey.stratosphericbaloon.db.launches

import android.content.Context
import io.github.gubarsergey.stratosphericbaloon.db.launches.enitity.Launch
import io.github.gubarsergey.stratosphericbaloon.helper.SharedPrefHelper
import io.github.gubarsergey.stratosphericbaloon.api.launch.LaunchesApi
import io.github.gubarsergey.stratosphericbaloon.db.RealmMapper
import io.github.gubarsergey.stratosphericbaloon.db.launches.realm.LaunchMapper
import io.github.gubarsergey.stratosphericbaloon.db.launches.realm.RealmLaunch
import io.reactivex.Observable
import io.realm.Realm
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import retrofit2.Retrofit

class LaunchesRepository(
    val context: Context,
    val retrofit: Retrofit,
    val realmMapper: LaunchMapper
) : AnkoLogger {
    fun getAllLaunches(): Observable<List<Launch>> {
        return retrofit.create(LaunchesApi::class.java)
            .getLaunches("Bearer ${SharedPrefHelper.getToken(context)}")
    }

    fun getNearLaunches(longitude: Double, latitude: Double): Observable<List<Launch>> {
        return retrofit.create(LaunchesApi::class.java)
            .getLaunchesWithinRadius("Bearer ${SharedPrefHelper.getToken(context)}", longitude, latitude)
    }

    fun getLaunchesFromDb(): List<Launch> {
        return Realm.getDefaultInstance().where(RealmLaunch::class.java).findAll().map { realmMapper.fromRealm(it) }
            .also { info("getLaunchesFromDb result = [$it]") }
    }

    fun saveLaunches(launches: List<Launch>) {
        info("saveLaunches launches = [$launches]")
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction { r ->
            val realmLaunches = launches.map { realmMapper.toRealm(it) }
            realmLaunches.forEach {
                r.insertOrUpdate(it)
            }
        }
    }
}