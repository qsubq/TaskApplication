package com.github.qsubq.taskapplication.app.presentation.screen.detail

import androidx.lifecycle.ViewModel
import com.github.qsubq.taskapplication.data.db.TaskModel
import io.realm.Realm

class DetailViewModel: ViewModel() {
    private val realm: Realm = Realm.getDefaultInstance()

    fun addTask(task:TaskModel){
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(task)
        realm.commitTransaction()
    }
}