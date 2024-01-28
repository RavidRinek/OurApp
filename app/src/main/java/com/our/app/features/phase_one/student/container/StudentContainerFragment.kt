package com.our.app.features.phase_one.student.container

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.our.app.MainActivity
import com.our.app.R
import com.our.app.base.BaseFragment
import com.our.app.databinding.FragmentStudentContainerBinding
import com.our.app.databinding.FragmentTeacherContainerBinding
import com.our.app.features.phase_one.teacher.container.TeacherContainerViewModel
import com.our.app.features.phase_one.teacher.container.TeacherContainerViewModelImpl
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
    private val binding by viewBinding(FragmentStudentContainerBinding::bind)

    @Inject
    lateinit var prefs: Prefs

    private val backPressedCallback = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
            Toast.makeText(requireContext(), "fdssfd", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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