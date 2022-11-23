package com.github.qsubq.taskapplication.data.repository

import android.content.Context
import android.icu.util.Calendar
import android.text.format.DateUtils
import com.github.qsubq.taskapplication.data.db.TaskModel
import com.github.qsubq.taskapplication.domain.repository.TaskRepository
import io.realm.Realm

class TaskRepositoryImpl(private val context: Context,private val realm: Realm) : TaskRepository {

    override fun getTasks(selectedData: Calendar): List<TaskModel> {
        val tasks = mutableListOf<TaskModel>()
        if (realm.where(TaskModel::class.java).findAll().size != 0) {
            tasks.clear()
            for (i in realm.where(TaskModel::class.java).findAll()
                .filter { d -> dealInSelectedDay(d, selectedData) }) {
                tasks.add(i)
            }
        }
        return tasks
    }

    override fun saveTask(task: TaskModel) {
        with(realm){
            beginTransaction()
            copyToRealmOrUpdate(task)
            commitTransaction()
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