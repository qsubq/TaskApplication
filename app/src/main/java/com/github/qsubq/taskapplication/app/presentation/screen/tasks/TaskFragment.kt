package com.github.qsubq.taskapplication.app.presentation.screen.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.qsubq.taskapplication.databinding.FragmentTaskBinding

class TaskFragment : Fragment() {
    private lateinit var binding: FragmentTaskBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentTaskBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}