package com.github.qsubq.taskapplication.app.presentation.screen.detail

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.icu.util.Calendar
import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.qsubq.taskapplication.R
import com.github.qsubq.taskapplication.app.presentation.utils.showToast
import com.github.qsubq.taskapplication.data.db.TaskModel
import com.github.qsubq.taskapplication.databinding.FragmentDetailBinding
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

        with(binding) {
            etTimeStart.setOnClickListener { setTimeStart() }
            etTimeEnd.setOnClickListener { setTimeEnd() }
            btnAdd.setOnClickListener { addData() }
            etDate.setOnClickListener { setDate() }
        }

    }

    private fun addData() {
        val task = TaskModel()

        with(task) {
            name = binding.etName.text.toString()
            description = binding.etDesc.text.toString()
            color = getRandomColor()
            date = DateUtils.formatDateTime(
                requireActivity(),
                dateAndTimeStart.timeInMillis,
                DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR
            )
            timeStart = dateAndTimeStart.get(Calendar.MILLISECONDS_IN_DAY) / 1000 / 60
            timeFinish = dateAndTimeFinish.get(Calendar.MILLISECONDS_IN_DAY) / 1000 / 60
            id = UUID.randomUUID().toString()
        }


        if (task.timeStart >= task.timeFinish || task.name == "") {
            if (task.timeStart >= task.timeFinish) {
                view?.let {
                    showToast("Time start cannot be more than time finish")
                }
            } else {
                view?.let {
                    showToast("Task name cannot be empty")
                }
            }
        } else {
            viewModel.addTask(task)
            findNavController().navigate(R.id.action_detailFragment_to_taskFragment)
        }
    }

    private fun setDate() {
        this.context?.let {
            DatePickerDialog(
                it, d,
                dateAndTimeStart.get(Calendar.YEAR),
                dateAndTimeStart.get(Calendar.MONTH),
                dateAndTimeStart.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun setTimeStart() {
        TimePickerDialog(
            this.context, t1,
            dateAndTimeStart.get(Calendar.HOUR_OF_DAY),
            dateAndTimeStart.get(Calendar.MINUTE), true
        ).show()
    }

    private fun setTimeEnd() {
        TimePickerDialog(
            this.context, t2,
            dateAndTimeFinish.get(Calendar.HOUR_OF_DAY),
            dateAndTimeFinish.get(Calendar.MINUTE), true
        ).show()
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
            with(dateAndTimeStart) {
                set(Calendar.HOUR_OF_DAY, hourOfDay)
                set(Calendar.MINUTE, minute)
            }
            setInitialTimeStart()
        }

    private var t2 =
        TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            with(dateAndTimeFinish) {
                set(Calendar.HOUR_OF_DAY, hourOfDay)
                set(Calendar.MINUTE, minute)
            }
            setInitialTimeEnd()
        }

    private var d =
        DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            with(dateAndTimeStart) {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, monthOfYear)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }
            setInitialDate()
        }

    private fun getRandomColor(): Int {
        val rnd = Random()
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }
}