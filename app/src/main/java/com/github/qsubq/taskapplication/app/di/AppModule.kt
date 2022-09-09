package com.github.qsubq.taskapplication.app.di

import com.github.qsubq.taskapplication.app.presentation.screen.detail.DetailViewModel
import com.github.qsubq.taskapplication.app.presentation.screen.tasks.TaskViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel<TaskViewModel> {
        TaskViewModel(useCase = get())
    }
    viewModel<DetailViewModel> {
        DetailViewModel(useCase = get())
    }
}