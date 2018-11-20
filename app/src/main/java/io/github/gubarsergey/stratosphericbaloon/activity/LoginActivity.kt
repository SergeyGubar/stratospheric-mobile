package io.github.gubarsergey.stratosphericbaloon.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.gson.GsonBuilder
import io.github.gubarsergey.stratosphericbaloon.R
import io.github.gubarsergey.stratosphericbaloon.constant.BASE_URL
import io.github.gubarsergey.stratosphericbaloon.enum.UserRole
import io.github.gubarsergey.stratosphericbaloon.retrofit.api.launch.LaunchesApi
import io.github.gubarsergey.stratosphericbaloon.retrofit.api.register.RegisterApi
import io.github.gubarsergey.stratosphericbaloon.retrofit.model.UserRegisterModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity(), AnkoLogger {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupListeners()
    }

    private fun setupListeners() {
        loginButton.setOnClickListener {
            /*val api = retrofit.create(RegisterApi::class.java)

                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    info("Got $it")
                }, {
                    info("Fail $it")
                })*/
            /*validateLogin({
                startActivity(intentFor<MainActivity>())
            }) {
                toast(getString(R.string.email_or_password_incorrect))
            }*/
        }
    }

    private fun validateLogin(
        doOnSuccess: () -> Unit,
        doOnFailure: () -> Unit = {}
    ) {
        if (emailEditText.text.length > 5) {
            doOnSuccess()
        } else {
            doOnFailure()
        }
    }
}

