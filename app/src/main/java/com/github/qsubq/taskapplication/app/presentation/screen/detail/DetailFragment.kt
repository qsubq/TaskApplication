package com.github.qsubq.taskapplication.app.presentation.screen.detail

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.github.qsubq.taskapplication.R
import com.github.qsubq.taskapplication.data.db.TaskModel
import com.github.qsubq.taskapplication.databinding.FragmentDetailBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val viewModel by viewModel<DetailViewModel>()

    private var dateAndTimeStart: Calendar = Calendar.getInstance()
    private var dateAndTimeFinish: Calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitialDate()
        setInitialTimeStart()
        setInitialTimeEnd()

        binding.etTimeStart.setOnClickListener { setTimeStart() }
        binding.etTimeEnd.setOnClickListener { setTimeEnd() }
        binding.btnAdd.setOnClickListener { addData() }
        binding.etDate.setOnClickListener { setDate() }
    }

    private fun addData() {
        val task = TaskModel()

        task.name = binding.etName.text.toString()
        task.description = binding.etDesc.text.toString()
        task.color = getRandomColor()

        task.date = DateUtils.formatDateTime(
            this.context,
            dateAndTimeStart.timeInMillis,
            DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR)

        task.timeStart = dateAndTimeStart.get(Calendar.MILLISECONDS_IN_DAY) / 1000 / 60
        task.timeFinish = dateAndTimeFinish.get(Calendar.MILLISECONDS_IN_DAY) / 1000 / 60
        task.id = UUID.randomUUID().toString()

        if (task.timeStart >= task.timeFinish || task.name == "") {

            if (task.timeStart >= task.timeFinish)
                view?.let {
                    Snackbar.make(it,
                        "Time start cannon be more than time finish",
                        Snackbar.LENGTH_LONG).show()
                }
            if (task.name == "")
                view?.let {
                    Snackbar.make(it,
                        "Task name cannot be empty",
                        Snackbar.LENGTH_LONG).show()
                }
        } else {
            try {
                viewModel.addTask(task)
            } catch (error: Exception) {
                println(error)
                view?.let { Snackbar.make(it, error.toString(), Snackbar.LENGTH_LONG).show() }
            }
            val navHostFragment =
                activity?.supportFragmentManager?.findFragmentById(R.id.nav_fragment) as NavHostFragment
            navHostFragment.navController.navigate(R.id.action_detailFragment_to_taskFragment)
        }

    }

    private fun setDate() {
        this.context?.let {
            DatePickerDialog(
                it, d,
                dateAndTimeStart.get(Calendar.YEAR),
                dateAndTimeStart.get(Calendar.MONTH),
                dateAndTimeStart.get(Calendar.DAY_OF_MONTH)
            )
                .show()
        }
    }

    private fun setTimeStart() {
        TimePickerDialog(
            this.context, t1,
            dateAndTimeStart.get(Calendar.HOUR_OF_DAY),
            dateAndTimeStart.get(Calendar.MINUTE), true
        )
            .show()
    }

    private fun setTimeEnd() {
        TimePickerDialog(
            this.context, t2,
            dateAndTimeFinish.get(Calendar.HOUR_OF_DAY),
            dateAndTimeFinish.get(Calendar.MINUTE), true
        )
            .show()
    }

    private fun setInitialDate() {
        binding.etDate.text = DateUtils.formatDateTime(
            this.context,
            dateAndTimeStart.timeInMillis,
            DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR

        )
    }

    private fun setInitialTimeStart() {
        binding.etTimeStart.text = DateUtils.formatDateTime(
            this.context,
            dateAndTimeStart.timeInMillis,
            DateUtils.FORMAT_SHOW_TIME
        )
    }

    private fun setInitialTimeEnd() {
        binding.etTimeEnd.text = DateUtils.formatDateTime(
            this.context,
            dateAndTimeFinish.timeInMillis,
            DateUtils.FORMAT_SHOW_TIME
        )
    }

    private var t1 =
        TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            dateAndTimeStart.set(Calendar.HOUR_OF_DAY, hourOfDay)
            dateAndTimeStart.set(Calendar.MINUTE, minute)
            setInitialTimeStart()
        }

    private var t2 =
        TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            dateAndTimeFinish.set(Calendar.HOUR_OF_DAY, hourOfDay)
            dateAndTimeFinish.set(Calendar.MINUTE, minute)
            setInitialTimeEnd()
        }

    private var d =
        DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            dateAndTimeStart.set(Calendar.YEAR, year)
            dateAndTimeStart.set(Calendar.MONTH, monthOfYear)
            dateAndTimeStart.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            setInitialDate()
        }
    private fun getRandomColor(): Int {
        return when (Random().nextInt(6)) {
            0 -> R.color.blue_100
            1 -> R.color.blue_300
            2 -> R.color.red
            3 -> R.color.orange
            4 -> R.color.yellow
            5 -> R.color.purple
            else -> {
                R.color.green
            }
        }
    }
}