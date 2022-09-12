package com.github.qsubq.taskapplication.app.presentation.screen.tasks

import com.github.qsubq.taskapplication.data.db.TaskModel
import com.github.qsubq.taskapplication.domain.usecase.GetTasksUseCase
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import android.icu.util.Calendar


class TaskViewModelTest {
    private val getTasksUseCase = mock<GetTasksUseCase>()

    @AfterEach
    fun afterEach(){
        Mockito.reset(getTasksUseCase)
    }

    @Test
    fun `should load data`(){
        val testSelectedDate = Calendar.getInstance()
        testSelectedDate.set(2022,11,23)

        val viewModel = TaskViewModel(getTasksUseCase)

        val testTaskModel = TaskModel()
        testTaskModel.name = "testNameTask"
        testTaskModel.timeStart = 1000000
        testTaskModel.timeFinish = 2000000

        Mockito.`when`(getTasksUseCase.invoke(testSelectedDate)).thenReturn(listOf(testTaskModel))

        viewModel.loadData(testSelectedDate)

        val expected = listOf(testTaskModel)
        val actual = viewModel.tasks

        Mockito.verify(getTasksUseCase.invoke(testSelectedDate),Mockito.times(1))
        Assertions.assertEquals(expected,actual)
    }


}