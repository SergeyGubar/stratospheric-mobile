package io.github.gubarsergey.stratosphericbaloon.ui.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.github.gubarsergey.stratosphericbaloon.App
import io.github.gubarsergey.stratosphericbaloon.R
import io.github.gubarsergey.stratosphericbaloon.ui.main.MainActivity
import io.github.gubarsergey.stratosphericbaloon.ui.register.RegisterActivity
import io.github.gubarsergey.stratosphericbaloon.helper.CredentialsValidator
import io.github.gubarsergey.stratosphericbaloon.helper.SharedPrefHelper
import io.github.gubarsergey.stratosphericbaloon.api.login.LoginApi
import io.github.gubarsergey.stratosphericbaloon.api.login.entity.UserLoginModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.*

class LoginActivity : AppCompatActivity(), AnkoLogger {

    private val retrofit by lazy {
        (application as App).getRetrofit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (SharedPrefHelper.isUserLoggedIn(this)) {
            startActivity(intentFor<MainActivity>())
            finish()
        }
        setupListeners()
    }

    private fun setupListeners() {
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            validateLogin({
                retrofit.create(LoginApi::class.java)
                    .login(
                        UserLoginModel(
                            email,
                            password
                        )
                    )
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ tokenModel ->
                        info("Successfully logged $tokenModel")
                        SharedPrefHelper.saveUserData(
                            this,
                            email,
                            password,
                            true,
                            tokenModel.token,
                            tokenModel.role
                        )
                        startActivity(intentFor<MainActivity>())
                        finish()
                    }, { ex ->
                        toast("Login failed!")
                        info("Login failed $ex")
                    })
            }) {
                toast(getString(R.string.email_or_password_incorrect))
            }
        }
        registerButton.setOnClickListener { startActivity(intentFor<RegisterActivity>()) }
    }

    private fun validateLogin(
        doOnSuccess: () -> Unit,
        doOnFailure: () -> Unit = {}
    ) {
        if (CredentialsValidator.validate(emailEditText.text.toString(), passwordEditText.text.toString())) {
            doOnSuccess()
        } else {
            doOnFailure()
        }
    }
}

