package com.github.qsubq.taskapplication.domain.usecase

import com.github.qsubq.taskapplication.data.db.TaskModel
import com.github.qsubq.taskapplication.domain.repository.TaskRepository

class SaveTasksUseCase(private val repository: TaskRepository) {
    operator fun invoke(task: TaskModel) = repository.saveTask(task)
}