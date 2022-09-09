package com.github.qsubq.taskapplication.app.di

import com.github.qsubq.taskapplication.domain.usecase.GetTasksUseCase
import com.github.qsubq.taskapplication.domain.usecase.SaveTasksUseCase
import org.koin.dsl.module

val domainModule = module {
    factory<GetTasksUseCase> {
        GetTasksUseCase(repository = get())
    }
    factory<SaveTasksUseCase> {
        SaveTasksUseCase(repository = get())
    }
}
