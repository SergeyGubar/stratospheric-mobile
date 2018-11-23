package io.github.gubarsergey.stratosphericbaloon.db

interface RealmMapper<Model, Realm> {

    fun fromRealm(realmObject: Realm): Model

    fun toRealm(modelObject: Model): Realm
}