package com.github.qsubq.taskapplication.app.presentation.screen.tasks

import android.app.Application
import android.icu.util.Calendar
import android.text.format.DateUtils
import androidx.lifecycle.AndroidViewModel
import com.github.qsubq.taskapplication.data.db.TaskModel
import io.realm.Realm


class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val realm = Realm.getDefaultInstance()
    private val context = application
    var tasks = mutableListOf<TaskModel>()

    fun loadData(selectedDate: Calendar) {

        if (realm.where(TaskModel::class.java).findAll().size != 0) {
            println("вот сколько записей " + realm.where(TaskModel::class.java).findAll().size)
            tasks.clear()
            for (i in realm.where(TaskModel::class.java).findAll()
                .filter { d -> dealInSelectedDay(d, selectedDate) }) {
                tasks.add(i)
                println("вот что прочитал " + i.name + " " + i.timeStart + " " + i.timeFinish)
            }
        }
    }

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