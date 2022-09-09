package com.github.qsubq.taskapplication.app.presentation.screen.tasks

import android.app.Application
import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import com.github.qsubq.taskapplication.data.db.TaskModel
import com.github.qsubq.taskapplication.domain.usecase.GetTasksUseCase


class TaskViewModel(private val useCase: GetTasksUseCase) : ViewModel() {
    var tasks = emptyList<TaskModel>()


    fun loadData(selectedData: Calendar) {
        tasks = useCase.invoke(selectedData)
    }


//    fun loadData(selectedDate: Calendar) {
//        if (realm.where(TaskModel::class.java).findAll().size != 0) {
//            tasks.clear()
//            for (i in realm.where(TaskModel::class.java).findAll()
//                .filter { d -> dealInSelectedDay(d, selectedDate) }) {
//                tasks.add(i)
//            }
//        }
//    }

//    private fun dealInSelectedDay(d: TaskModel, selectedDate: Calendar): Boolean {
//        val dealDate: String? = d.date
//        val selectedDateStr: String? = DateUtils.formatDateTime(
//            context,
//            selectedDate.timeInMillis,
//            DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR
//        )
//        if (dealDate != null) {
//            return dealDate == selectedDateStr
//        }
//        return false
//    }
}