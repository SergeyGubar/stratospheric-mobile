package io.github.gubarsergey.stratosphericbaloon.ui.register

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.github.gubarsergey.stratosphericbaloon.App
import io.github.gubarsergey.stratosphericbaloon.R
import io.github.gubarsergey.stratosphericbaloon.ui.main.MainActivity
import io.github.gubarsergey.stratosphericbaloon.api.register.entity.UserRole
import io.github.gubarsergey.stratosphericbaloon.helper.CredentialsValidator
import io.github.gubarsergey.stratosphericbaloon.helper.SharedPrefHelper
import io.github.gubarsergey.stratosphericbaloon.api.register.RegisterApi
import io.github.gubarsergey.stratosphericbaloon.api.register.entity.UserRegisterModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast

class RegisterActivity : AppCompatActivity(), AnkoLogger {

    private val retrofit by lazy {
        (application as App).getRetrofit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setupListeners()
    }

    private fun setupListeners() {
        registerButton.setOnClickListener {
            val email = registerEmailEditText.text.toString()
            val password = registerPasswordEditText.text.toString()
            validateRegister(email, password, {
                retrofit.create(RegisterApi::class.java)
                    .register(
                        UserRegisterModel(
                            email,
                            password,
                            true,
                            UserRole.ADMIN.role
                        )
                    )
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ tokenModel ->
                        info("Successfully registered $tokenModel")
                        SharedPrefHelper.saveUserData(this, email, password, true, tokenModel.token)
                        startActivity(intentFor<MainActivity>())
                        finish()
                    }) { ex ->
                        toast("Register failed!")
                        info("Register failed $ex")
                    }
            }) {
                toast("Credentials don't satisfy security requirements!")
            }
        }
    }

    private fun validateRegister(
        email: String, password: String,
        doOnSuccess: () -> Unit,
        doOnFailure: () -> Unit = {}
    ) {
        if (CredentialsValidator.validate(
                email,
                password
            )
        ) {
            doOnSuccess()
        } else {
            doOnFailure()
        }
    }
}
