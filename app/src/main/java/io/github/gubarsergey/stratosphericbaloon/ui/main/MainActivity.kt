package io.github.gubarsergey.stratosphericbaloon.ui.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import io.github.gubarsergey.stratosphericbaloon.App
import io.github.gubarsergey.stratosphericbaloon.R
import io.github.gubarsergey.stratosphericbaloon.extension.inTransaction
import io.github.gubarsergey.stratosphericbaloon.helper.SharedPrefHelper
import io.github.gubarsergey.stratosphericbaloon.ui.launches.LaunchesFragment
import io.github.gubarsergey.stratosphericbaloon.ui.launches.NewLaunchFragment
import io.github.gubarsergey.stratosphericbaloon.ui.photo.PhotosFragment
import io.github.gubarsergey.stratosphericbaloon.api.login.LoginApi
import io.github.gubarsergey.stratosphericbaloon.api.login.entity.UserLoginModel
import io.github.gubarsergey.stratosphericbaloon.ui.settings.SettingsFragment
import io.github.gubarsergey.stratosphericbaloon.ui.weather.WeatherFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header.*
import kotlinx.android.synthetic.main.nav_header.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import java.lang.IllegalStateException

class MainActivity : AppCompatActivity(), AnkoLogger {

    private val retrofit by lazy {
        (application as App).getRetrofit()
    }

    private lateinit var disposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.inTransaction {
            add(R.id.main_container, NewLaunchFragment())
        }
        nav_view.getHeaderView(0).nav_header_title.text = SharedPrefHelper.getUserEmail(this)
        loginUser()
        setupDrawer()
    }

    private fun setupDrawer() {
        nav_view.setNavigationItemSelectedListener { item ->
            item.isChecked = true
            drawer_layout.closeDrawers()
            val fragment: Fragment = when (item.itemId) {
                R.id.nav_launches -> LaunchesFragment()
                R.id.nav_photos -> PhotosFragment()
                R.id.nav_new_launch -> NewLaunchFragment()
                R.id.nav_weather -> WeatherFragment()
                R.id.nav_settings -> SettingsFragment()
                else -> throw IllegalStateException()
            }
            supportFragmentManager.inTransaction {
                replace(R.id.main_container, fragment)
            }
            true
        }
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
