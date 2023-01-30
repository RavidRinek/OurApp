package com.our.app.features.phase_one.teacherlobby

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.our.app.R
import com.our.app.base.BaseFragment
import com.our.app.databinding.FragmentStudentLobbyBinding
import com.our.app.databinding.FragmentTeacherLobbyBinding
import com.our.app.features.phase_one.studentlobby.StudentLobbyViewModel
import com.our.app.features.phase_one.studentlobby.StudentLobbyViewModelImpl
import com.our.app.utilities.bindingDelegates.viewBinding
import com.our.domain.features.phase_one.models.local.GotTeacherCreateInfo
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TeacherLobbyFragment : BaseFragment<TeacherLobbyViewModel>(R.layout.fragment_teacher_lobby) {

    override val viewModel: TeacherLobbyViewModel by viewModels<TeacherLobbyViewModelImpl>()
    private val binding by viewBinding(FragmentTeacherLobbyBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnTeacherCreateInfo.setOnClickListener {
//            it.isEnabled = false
            viewModel.postTeacherCreateInfo(binding.cvTeacherCreateInfo.getTeachInfo())
        }
    }

    override fun observeData() {
        super.observeData()
        viewModel.teacherLobbyResponseLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is GotTeacherCreateInfo -> {
//                    binding.btnTeacherCreateInfo.isEnabled = true
                    println("it.teacherProfile: ${it.teacherProfile}")
                }
                else -> Unit
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = TeacherLobbyFragment()
    }
}