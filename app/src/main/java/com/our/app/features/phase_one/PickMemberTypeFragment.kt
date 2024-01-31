package com.our.app.features.phase_one

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.our.app.R
import com.our.app.databinding.FragmentPickMemberTypeBinding
import com.our.app.utilities.bindingDelegates.viewBinding
import com.our.data.base.datasources.Prefs
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class PickMemberTypeFragment : Fragment() {

    private val binding by viewBinding(FragmentPickMemberTypeBinding::bind)

    @Inject
    lateinit var prefs: Prefs

    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            Toast.makeText(requireContext(), "fdssfd", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pick_member_type, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (prefs.contains(Prefs.TEACHER_ID)) {
            moveToTeacherContainer()
        } else if (prefs.contains(Prefs.STUDENT_ID)) {
            moveToStudentContainer()
        }

        binding.btnTeacher.setOnClickListener { moveToTeacherContainer() }
        binding.btnStudent.setOnClickListener { moveToStudentContainer() }
    }

    private fun moveToTeacherContainer() {
        findNavController().navigate(R.id.action_pickMemberTypeFragment_to_teacherContainerFragment)
    }

    private fun moveToStudentContainer() {
        findNavController().navigate(R.id.action_pickMemberTypeFragment_to_studentContainerFragment)
    }

    companion object {
        @JvmStatic
        fun newInstance() = PickMemberTypeFragment()
    }
}