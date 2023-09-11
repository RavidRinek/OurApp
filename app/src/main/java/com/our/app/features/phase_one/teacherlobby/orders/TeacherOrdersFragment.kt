package com.our.app.features.phase_one.teacherlobby.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.our.app.databinding.FragmentTeacherOrdersBinding
import com.our.app.features.phase_one.teacherlobby.teacher_lobby.TeacherUpcomingLessonsAdapter
import com.our.domain.features.phase_one.models.remote.TeacherOrder

class TeacherOrdersFragment : Fragment() {

    private var _binding: FragmentTeacherOrdersBinding? = null
    private val binding get() = _binding!!
    private lateinit var teacherUpcomingLessonsAdapter: TeacherUpcomingLessonsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeacherOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("TeacherOrdersFragment: onViewCreated")
        teacherUpcomingLessonsAdapter =
            TeacherUpcomingLessonsAdapter(arguments?.getSerializable(K_ORDERS) as List<TeacherOrder>)
        binding.rvTeacherLessons.adapter = teacherUpcomingLessonsAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val K_ORDERS = "teacher_orders"

        @JvmStatic
        fun newInstance() =
            TeacherOrdersFragment()
    }
}