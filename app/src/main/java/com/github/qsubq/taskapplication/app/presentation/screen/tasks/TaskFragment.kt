package com.github.qsubq.taskapplication.app.presentation.screen.tasks

import android.graphics.Color
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Space
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.github.qsubq.taskapplication.R
import com.github.qsubq.taskapplication.databinding.FragmentTaskBinding

class TaskFragment : Fragment() {
    private lateinit var binding: FragmentTaskBinding
    private val viewModel: TaskViewModel by viewModels()
    private var hourHeight = 120.0

    @RequiresApi(Build.VERSION_CODES.N)
    private var selectedDate = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentTaskBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnDetail.setOnClickListener {
            val navHostFragment =
                activity?.supportFragmentManager?.findFragmentById(R.id.nav_fragment) as NavHostFragment
            navHostFragment.navController.navigate(R.id.action_taskFragment_to_detailFragment)
        }

        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate.set(year, month, dayOfMonth)
            update()
        }
        update()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun update() {
        viewModel.loadData(selectedDate)

        val dealsColumns: LinearLayout = binding.dealsColumns
        dealsColumns.removeAllViews()

        val hours = LinearLayout(this.context)
        val lph = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT)
        lph.setMargins(20, 3, 10, 0)
        hours.layoutParams = lph
        hours.orientation = LinearLayout.VERTICAL

        for (i in 0..23) {
            val hour = TextView(this.context)
            val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
            lp.setMargins(0, 0, 0, 0)
            hour.setBackgroundColor(Color.CYAN)
            hour.layoutParams = lp

            hour.text = convertIntToTime(i)
            hour.setTextColor(Color.BLACK)
            hour.layoutParams.height = hourHeight.toInt()
            hour.layoutParams.width = 120
            hours.addView(hour)
        }
        dealsColumns.addView(hours)


        if (viewModel.tasks.size != 0) {

            val taskWidth = 1000 / viewModel.tasks.size

            for (i in viewModel.tasks) {

                val dealColumn = LinearLayout(this.context)
                val lpd = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT)
                lpd.setMargins(0, 3, 5, 0)
                dealColumn.layoutParams = lpd
                dealColumn.orientation = LinearLayout.VERTICAL

                val block = Space(this.context)
                block.setBackgroundColor(Color.MAGENTA)
                block.layoutParams =
                    LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT)
                block.layoutParams.height = (hourHeight / 60 * i.timeStart).toInt()
                block.layoutParams.width = taskWidth

                dealColumn.addView(block)

                val deal = TextView(this.context)
                val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT)
                lp.setMargins(0, 0, 0, 0)
                this.context?.let { ContextCompat.getColor(it, R.color.teal_lite) }
                    ?.let { deal.setBackgroundColor(it) }
                deal.layoutParams = lp
                deal.setPadding(15, 0, 0, 0)

                if (i.description != "") deal.text =
                    getString(R.string.task_desc, i.name, i.description)
                else deal.text = i.name
                deal.setTextColor(Color.BLACK)
                deal.layoutParams.height = (hourHeight / 60 * (i.timeFinish - i.timeStart)).toInt()
                deal.layoutParams.width = taskWidth
                dealColumn.addView(deal)

                val block2 = Space(this.context)
                block2.setBackgroundColor(Color.MAGENTA)
                block2.layoutParams =
                    LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT)
                block2.layoutParams.height = (hourHeight / 60 * (24 * 60 - i.timeFinish)).toInt()
                block2.layoutParams.width = taskWidth

                dealColumn.addView(block2)
                dealsColumns.addView(dealColumn)
            }
        }
    }

    private fun convertIntToTime(i: Int): String {
        return if (i < 10) "0$i:00" else "$i:00"
    }
}