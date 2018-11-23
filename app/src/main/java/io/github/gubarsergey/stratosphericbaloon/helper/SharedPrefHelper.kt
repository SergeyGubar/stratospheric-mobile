@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package io.github.gubarsergey.stratosphericbaloon.helper

import android.content.Context
import io.github.gubarsergey.stratosphericbaloon.constant.AUTH_TOKEN
import io.github.gubarsergey.stratosphericbaloon.constant.IS_USER_LOGGED_IN
import io.github.gubarsergey.stratosphericbaloon.constant.USER_EMAIL
import io.github.gubarsergey.stratosphericbaloon.constant.USER_PASSWORD
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.defaultSharedPreferences
import org.jetbrains.anko.info

object SharedPrefHelper : AnkoLogger {
    fun saveUserData(context: Context, email: String, password: String, isLoggedIn: Boolean, token: String) {
        info("saveUserData isLoggedIn = [$isLoggedIn] token = [$token]")
        context.defaultSharedPreferences
            .edit()
            .putBoolean(IS_USER_LOGGED_IN, true)
            .putString(USER_EMAIL, email)
            .putString(USER_PASSWORD, password)
            .putString(AUTH_TOKEN, token)
            .apply()
    }

    fun isUserLoggedIn(context: Context): Boolean {
        info("isUserLoggedIn")
        return context.defaultSharedPreferences.getBoolean(IS_USER_LOGGED_IN, false)
    }

    fun getToken(context: Context): String {
        info("getToken")
        return context.defaultSharedPreferences.getString(AUTH_TOKEN, "")
    }

    // TODO: Security actually cries here
    fun getUserEmail(context: Context): String {
        info("getUserEmail")
        return context.defaultSharedPreferences.getString(USER_EMAIL, "")
    }

    fun getUserPassword(context: Context): String {
        info("getUserPassword")
        return context.defaultSharedPreferences.getString(USER_PASSWORD, "")
    }
}