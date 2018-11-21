package io.github.gubarsergey.stratosphericbaloon.helper

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

object CredentialsValidator : AnkoLogger {
    fun validate(email: String, password: String) =
        (email.length > 5 && password.length > 5).also { info("validate $email $password result = $it") }
}