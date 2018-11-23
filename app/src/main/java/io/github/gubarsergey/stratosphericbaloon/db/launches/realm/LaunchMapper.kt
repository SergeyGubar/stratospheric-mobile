package io.github.gubarsergey.stratosphericbaloon.db.launches.realm

import io.github.gubarsergey.stratosphericbaloon.db.RealmMapper
import io.github.gubarsergey.stratosphericbaloon.db.launches.enitity.Launch
import io.github.gubarsergey.stratosphericbaloon.db.launches.realm.RealmLaunch

class LaunchMapper : RealmMapper<Launch, RealmLaunch> {

    override fun fromRealm(realmObject: RealmLaunch): Launch {
        return Launch(
            realmObject.id,
            realmObject.name,
            realmObject.latitude,
            realmObject.longitude,
            realmObject.time
        )
    }

    override fun toRealm(modelObject: Launch): RealmLaunch {
        val realmLaunch = RealmLaunch()
        realmLaunch.id = modelObject.id
        realmLaunch.latitude = modelObject.latitude
        realmLaunch.longitude = modelObject.longitude
        realmLaunch.name = modelObject.name
        realmLaunch.time = modelObject.time
        return realmLaunch
    }

}