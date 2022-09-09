package com.github.qsubq.taskapplication.app.di

import com.github.qsubq.taskapplication.data.repository.TaskRepositoryImpl
import com.github.qsubq.taskapplication.domain.repository.TaskRepository
import io.realm.Realm
import io.realm.RealmConfiguration
import org.koin.dsl.module

val dataModule = module {
    single<Realm> {
        Realm.init(get())
        val realmConfiguration = RealmConfiguration
            .Builder()
            .name("task.db")
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(0)
            .allowWritesOnUiThread(true)
            .allowQueriesOnUiThread(true)
            .build()
        Realm.setDefaultConfiguration(realmConfiguration)
        Realm.getDefaultInstance()
    }
    single<TaskRepository> {
        TaskRepositoryImpl(context = get(), realm = get())
    }
}