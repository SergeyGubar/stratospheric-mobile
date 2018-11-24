@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package io.github.gubarsergey.stratosphericbaloon.helper

import android.content.Context
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.defaultSharedPreferences
import org.jetbrains.anko.info

private const val IS_USER_LOGGED_IN = "IS_USER_LOGGED_IN"
private const val USER_EMAIL = "USER_EMAIL"
private const val USER_PASSWORD = "USER_PASSWORD"
private const val AUTH_TOKEN = "AUTH_TOKEN"
private const val ROLE = "ROLE"

object SharedPrefHelper : AnkoLogger {

    fun saveUserData(
        context: Context,
        email: String,
        password: String,
        isLoggedIn: Boolean,
        token: String,
        role: String
    ) {
        info("saveUserData isLoggedIn = [$isLoggedIn] token = [$token]")
        context.defaultSharedPreferences
            .edit()
            .putBoolean(IS_USER_LOGGED_IN, isLoggedIn)
            .putString(USER_EMAIL, email)
            .putString(USER_PASSWORD, password)
            .putString(AUTH_TOKEN, token)
            .putString(ROLE, role)
            .apply()
    }

    fun cleanCurrentUser(context: Context) {
        info("cleanCurrentUser")
        saveUserData(context, "", "", false, "", "")
    }

    fun isUserLoggedIn(context: Context): Boolean {
        info("isUserLoggedIn")
        return context.defaultSharedPreferences.getBoolean(IS_USER_LOGGED_IN, false)
    }

    fun getToken(context: Context): String {
        info("getToken")
        return context.defaultSharedPreferences.getString(AUTH_TOKEN, "")
    }

    fun getRole(context: Context): String {
        info("getRole")
        return context.defaultSharedPreferences.getString(ROLE, "")
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