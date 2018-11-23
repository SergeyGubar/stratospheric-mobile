package io.github.gubarsergey.stratosphericbaloon

import android.app.Application
import com.google.gson.GsonBuilder
import io.github.gubarsergey.stratosphericbaloon.api.BASE_URL
import io.github.gubarsergey.stratosphericbaloon.helper.DatabaseKeyConverter
import io.github.gubarsergey.stratosphericbaloon.helper.DatabaseKeyGenerator
import io.realm.Realm
import io.realm.RealmConfiguration
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.anko.defaultSharedPreferences
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val DATABASE_ENCRYPTION_KEY = "DATABASE_ENCRYPTION_KEY"

class App : Application() {


    private val gson = GsonBuilder().setLenient().create()

    private val okHttp = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttp)
        .build()

    fun getRetrofit() = retrofit

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        val realmBuilder = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()

        defaultSharedPreferences.apply {
            val key = if (contains(DATABASE_ENCRYPTION_KEY)) {
                DatabaseKeyConverter.keyToByteArray(getString(DATABASE_ENCRYPTION_KEY, "")!!)
            } else {
                DatabaseKeyGenerator.generateKey().also { key ->
                    edit().putString(DATABASE_ENCRYPTION_KEY, DatabaseKeyConverter.keyToString(key)).apply()
                }
            }
            realmBuilder.encryptionKey(key)
        }
    }
}