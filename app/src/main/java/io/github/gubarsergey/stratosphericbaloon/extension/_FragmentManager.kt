package io.github.gubarsergey.stratosphericbaloon.extension

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
    val transaction = beginTransaction()
    transaction.func()
    transaction.commitAllowingStateLoss()
}

inline fun FragmentManager.inTransactionWithBackStackAndName(name: String?, func: FragmentTransaction.() -> Unit) {
    val transaction = beginTransaction()
    transaction.func()
    transaction.addToBackStack(name)
    transaction.commitAllowingStateLoss()
}

inline fun FragmentManager.inTransactionWithBackStack(func: FragmentTransaction.() -> Unit) {
    val transaction = beginTransaction()
    transaction.func()
    transaction.addToBackStack(null)
    transaction.commitAllowingStateLoss()
}