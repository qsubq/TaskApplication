package com.github.qsubq.taskapplication.app.presentation.screen.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.qsubq.taskapplication.data.db.TaskModel
import io.realm.Realm
import java.util.*

class TaskViewModel: ViewModel() {
    private var realm: Realm = Realm.getDefaultInstance()
}