package com.our.app.features.phase_one.student.container

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.our.app.MainActivity
import com.our.app.R
import com.our.app.base.BaseFragment
import com.our.app.databinding.FragmentStudentContainerBinding
import com.our.app.utilities.bindingDelegates.viewBinding
import com.our.data.base.datasources.Prefs
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class StudentContainerFragment :
    BaseFragment<StudentContainerViewModel>(R.layout.fragment_student_container) {

    override val viewModel: StudentContainerViewModel by viewModels<StudentContainerViewModelImpl>()

    @Inject
    lateinit var prefs: Prefs

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).onFragmentChanged(this)
        if (prefs.getBoolean(Prefs.COMPLETED_STUDENT_FULL_REGISTRATION)) {
            moveToStudentLobby()
        } else {
            moveToStudentFindLesson()
        }
    }

    private fun moveToStudentLobby() {
        findNavController().navigate(R.id.action_studentContainerFragment_to_studentLobbyFragment)
    }

    private fun moveToStudentFindLesson() {
        findNavController().navigate(R.id.action_studentContainerFragment_to_studentFindLessonFragment)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StudentContainerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}