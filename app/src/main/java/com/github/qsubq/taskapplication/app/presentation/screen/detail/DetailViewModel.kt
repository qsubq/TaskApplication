package com.github.qsubq.taskapplication.app.presentation.screen.detail

import androidx.lifecycle.ViewModel
import com.github.qsubq.taskapplication.data.db.TaskModel
import com.github.qsubq.taskapplication.domain.usecase.SaveTasksUseCase
import io.realm.Realm


class DetailViewModel(private val useCase: SaveTasksUseCase) : ViewModel() {
    fun addTask(task: TaskModel) {
        useCase.invoke(task)
    }
}