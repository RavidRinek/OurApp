package com.our.app.features.phase_one.student.student_lobby

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.our.app.R
import com.our.app.base.BaseFragment
import com.our.app.databinding.FragmentStudentLobbyBinding
import com.our.app.features.phase_one.teacher.teacher_lobby.TeacherUpcomingLessonsAdapter
import com.our.app.utilities.bindingDelegates.viewBinding
import com.our.domain.features.phase_one.models.local.GotStudentUpcomingLessons
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudentLobbyFragment : BaseFragment<StudentLobbyViewModel>(R.layout.fragment_student_lobby) {

    override val viewModel: StudentLobbyViewModel by viewModels<StudentLobbyViewModelImpl>()
    private val binding by viewBinding(FragmentStudentLobbyBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun observeData() {
        super.observeData()
        viewModel.studentLobbyLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is GotStudentUpcomingLessons -> {
                    binding.rvStudentLessons.adapter =
                        TeacherUpcomingLessonsAdapter(it.upcomingLessons)
                }

                else -> Unit
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StudentLobbyFragment()
    }
}