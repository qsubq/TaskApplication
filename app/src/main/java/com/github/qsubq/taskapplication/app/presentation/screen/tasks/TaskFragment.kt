package com.github.qsubq.taskapplication.app.presentation.screen.tasks

import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Space
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.github.qsubq.taskapplication.R
import com.github.qsubq.taskapplication.databinding.FragmentTaskBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val layoutSize = 120
private const val layoutHeight = layoutSize / 60
private const val blockWidth = 800

class TaskFragment : Fragment() {
    private lateinit var binding: FragmentTaskBinding
    private val viewModel by viewModel<TaskViewModel>()
    private var selectedDate = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentTaskBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnDetail.setOnClickListener {
            val navHostFragment =
                activity?.supportFragmentManager?.findFragmentById(R.id.nav_fragment) as NavHostFragment
            navHostFragment.navController.navigate(R.id.action_taskFragment_to_detailFragment)
        }
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate.set(year, month, dayOfMonth)
            updateTasks()
        }
        updateTasks()
    }


    private fun updateTasks() {
        viewModel.loadData(selectedDate)
        val dealsColumns: LinearLayout = binding.dealsColumns
        dealsColumns.removeAllViews()

        val hoursLL = LinearLayout(this.context)
        val lph = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        lph.setMargins(18, 3, 10, 0)
        hoursLL.layoutParams = lph
        hoursLL.orientation = LinearLayout.VERTICAL

        for (i in 0..23) {
            val hourTV = TextView(this.context)
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.setMargins(0, 0, 0, 0)

            with(hourTV) {
                layoutParams = lp
                text = convertIntToTime(i)
                layoutParams.height = layoutSize
                layoutParams.width = layoutSize
            }


            hoursLL.addView(hourTV)
        }
        dealsColumns.addView(hoursLL)


        if (viewModel.tasks.isNotEmpty()) {
            for (i in viewModel.tasks) {
                val taskColumnLL = LinearLayout(this.context)
                val lpd = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                lpd.setMargins(0, 3, 5, 0)
                taskColumnLL.layoutParams = lpd
                taskColumnLL.orientation = LinearLayout.VERTICAL

                val blockStart = Space(this.context)
                blockStart.layoutParams =
                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                blockStart.layoutParams.height = (layoutHeight * i.timeStart)
                blockStart.layoutParams.width = blockWidth / viewModel.tasks.size

                taskColumnLL.addView(blockStart)

                val taskTV = TextView(this.context)
                val lp = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                lp.setMargins(0, 0, 0, 0)

                i.color?.let { taskTV.setBackgroundColor(it) }

                taskTV.layoutParams = lp
                taskTV.setPadding(15, 0, 0, 0)
                if (i.description != "") taskTV.text =
                    getString(R.string.task_desc, i.name, i.description)
                else taskTV.text = i.name
                taskTV.layoutParams.height = (layoutHeight * (i.timeFinish - i.timeStart))
                taskTV.layoutParams.width = blockWidth / viewModel.tasks.size
                taskColumnLL.addView(taskTV)

                val blockFinish = Space(this.context)
                blockFinish.layoutParams =
                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                blockFinish.layoutParams.height = (layoutHeight * (24 * 60 - i.timeFinish))
                blockFinish.layoutParams.width = blockWidth / viewModel.tasks.size

                taskColumnLL.addView(blockFinish)
                dealsColumns.addView(taskColumnLL)
            }
        }
    }

    private fun convertIntToTime(i: Int): String {
        return if (i < 10) "0$i:00" else "$i:00"
    }
}