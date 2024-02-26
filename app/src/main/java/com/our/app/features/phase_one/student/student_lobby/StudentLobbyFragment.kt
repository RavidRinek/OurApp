package com.our.app.features.phase_one.student.student_lobby

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.our.app.R
import com.our.app.base.BaseFragment
import com.our.app.databinding.FragmentStudentLobbyBinding
import com.our.app.features.phase_one.common.UpcomingLessonsAdapter
import com.our.app.utilities.bindingDelegates.viewBinding
import com.our.domain.features.phase_one.models.local.GotStudentUpcomingLessons
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudentLobbyFragment : BaseFragment<StudentLobbyViewModel>(R.layout.fragment_student_lobby) {

    override val viewModel: StudentLobbyViewModel by viewModels<StudentLobbyViewModelImpl>()
    private val binding by viewBinding(FragmentStudentLobbyBinding::bind)
    private var allowBackPress: Boolean = false

    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (allowBackPress) {
                requireActivity().moveTaskToBack(true)
            } else {
                Handler(Looper.getMainLooper()).postDelayed({
                    allowBackPress = false
                }, 1_500)
                Toast.makeText(requireContext(), "Press again to exit.", Toast.LENGTH_SHORT).show()
            }
            allowBackPress = true
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.tvAddLesson.setOnClickListener {
            findNavController().navigate(R.id.action_studentLobbyFragment_to_studentFindLessonFragment)
        }
    }

    override fun observeData() {
        super.observeData()
        viewModel.studentLobbyLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is GotStudentUpcomingLessons -> {
                    binding.rvStudentLessons.adapter =
                        UpcomingLessonsAdapter(it.upcomingLessons)
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