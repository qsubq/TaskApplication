package com.github.qsubq.taskapplication.domain.usecase

import android.icu.util.Calendar
import com.github.qsubq.taskapplication.domain.repository.TaskRepository

class GetTasksUseCase(private val repository: TaskRepository) {
    operator fun invoke(selectedData: Calendar) = repository.getTasks(selectedData)
}