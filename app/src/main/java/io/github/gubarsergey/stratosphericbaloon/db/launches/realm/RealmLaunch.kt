package io.github.gubarsergey.stratosphericbaloon.db.launches.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class RealmLaunch: RealmObject() {
    @PrimaryKey
    var id: String = UUID.randomUUID().toString()
    var name: String = ""
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    var time: String = ""
}