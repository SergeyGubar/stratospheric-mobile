package io.github.gubarsergey.stratosphericbaloon.extension

import android.view.View


fun View.makeVisible() {
    this.visibility = View.VISIBLE
}

fun View.makeInvisible() {
    this.visibility = View.INVISIBLE
}

fun View.makeGone() {
    this.visibility = View.GONE
}

fun View.isVisible() = this.visibility == View.VISIBLE


fun makeVisible(vararg view: View) {
    view.forEach {
        it.makeVisible()
    }
}


fun makeVisible(views: Iterable<View>) {
    views.forEach {
        it.makeVisible()
    }
}

fun makeGone(vararg view: View) {
    view.forEach {
        it.makeGone()
    }
}

fun makeGone(views: Iterable<View>) {
    views.forEach {
        it.makeGone()
    }
}

fun makeInvisible(vararg view: View) {
    view.forEach {
        it.makeInvisible()
    }
}

fun makeInvisible(views: Iterable<View>) {
    views.forEach {
        it.makeInvisible()
    }
}