package com.github.qsubq.taskapplication.app.presentation.screen.tasks

import android.app.Application
import android.icu.util.Calendar
import android.os.Build
import android.text.format.DateUtils
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.github.qsubq.taskapplication.data.db.TaskModel
import io.realm.Realm

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val realm: Realm = Realm.getDefaultInstance()
    var tasksLiveData = MutableLiveData<MutableList<TaskModel>>()
    private val context = application

    @RequiresApi(Build.VERSION_CODES.N)
    fun loadData(selectedDate: Calendar) {

        if (realm.where(TaskModel::class.java).findAll().size != 0) {
            tasksLiveData.value?.clear()
            for (i in realm.where(TaskModel::class.java).findAll()
                .filter { d -> dealInSelectedDay(d,selectedDate) }) {
                tasksLiveData.value?.add(i)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun dealInSelectedDay(d: TaskModel, selectedDate: Calendar): Boolean {
        val dealDate: String? = d.date
        val selectedDateStr: String? = DateUtils.formatDateTime(
            context,
            selectedDate.timeInMillis,
            DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR
        )
        if (dealDate != null) {
            return dealDate == selectedDateStr
        }
        return false
    }
}