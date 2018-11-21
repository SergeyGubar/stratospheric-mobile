package io.github.gubarsergey.stratosphericbaloon.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.gubarsergey.stratosphericbaloon.App
import io.github.gubarsergey.stratosphericbaloon.R
import io.github.gubarsergey.stratosphericbaloon.helper.SharedPrefHelper
import io.github.gubarsergey.stratosphericbaloon.retrofit.api.register.LoginApi
import io.github.gubarsergey.stratosphericbaloon.retrofit.model.UserLoginModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity(), AnkoLogger {

    private val retrofit by lazy {
        (application as App).getRetrofit()
    }

    private lateinit var disposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loginUser()
    }

    private fun loginUser() {
        val email = SharedPrefHelper.getUserEmail(this)
        val password = SharedPrefHelper.getUserPassword(this)
        disposable = retrofit.create(LoginApi::class.java)
            .login(UserLoginModel(email, password))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ tokenModel ->
                info("Successfully logged $tokenModel")
                SharedPrefHelper.saveUserData(this, email, password, true, tokenModel.token)
            }, { ex ->
                toast("Login failed!")
                info("Login failed $ex")
            })
    }

    override fun onStop() {
        super.onStop()
        disposable.dispose()
    }
}
