package com.github.qsubq.taskapplication.domain.repository

import android.icu.util.Calendar
import com.github.qsubq.taskapplication.data.db.TaskModel

interface TaskRepository {
    fun getTasks(selectedData: Calendar): List<TaskModel>
    fun saveTask(task: TaskModel)
}