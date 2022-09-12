package com.github.qsubq.taskapplication.app.presentation.screen.tasks

import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import com.github.qsubq.taskapplication.data.db.TaskModel
import com.github.qsubq.taskapplication.domain.usecase.GetTasksUseCase


class TaskViewModel(private val useCase: GetTasksUseCase) : ViewModel() {
    var tasks = emptyList<TaskModel>()

    fun loadData(selectedData: Calendar) {
        tasks = useCase.invoke(selectedData)
    }
}