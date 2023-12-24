package com.our.app.features.phase_one.teacherlobby.personal_info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.our.app.R
import com.our.app.base.BaseFragment
import com.our.app.databinding.FragmentTeacherPersonalInfoBinding
import com.our.app.features.phase_one.studentlobby.order_lesson.OrderLessonFragment
import com.our.app.features.phase_one.studentlobby.order_lesson.OrderLessonUi
import com.our.app.features.phase_one.teacherlobby.teacher_lobby.TeacherLobbyViewModel
import com.our.app.features.phase_one.teacherlobby.teacher_lobby.TeacherLobbyViewModelImpl
import com.our.domain.features.phase_one.models.remote.TeacherProfile
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TeacherPersonalInfoFragment :
    BaseFragment<TeacherPersonalInfoViewModel>(R.layout.fragment_teacher_personal_info) {

    override val viewModel: TeacherPersonalInfoViewModel by viewModels<TeacherPersonalInfoViewModelImpl>()
    private var _binding: FragmentTeacherPersonalInfoBinding? = null
    private val binding get() = _binding!!
    private val teachInfoHashMap = HashMap<String, String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeacherPersonalInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.btnTeacherCreateInfo.setOnClickListener {
            val filledInfo = getTeachInfo()
            if (filledInfo.entries.first().value.isNotEmpty()) {
                it.isEnabled = false
                viewModel.postTeacherCreateInfo(filledInfo)
            }
            viewLifecycleOwner.lifecycleScope.launch {
                delay(1_500)
                findNavController().navigate(R.id.action_teacherPersonalInfoFragment_to_teacherKnowlageInfoFragment)
            }
        }
    }

    override fun observeData() {
        super.observeData()
        viewModel.teacherPersonalInfoResponseLiveData.observe {
            binding.apply {
                it.teacherProfile.apply {
                    etName.setText(teacherName)
                    etLastName.setText(teacherLastName)
                    etPhone.setText(teacherPhone)
                    etMail.setText(teacherMail)
                    etBd.setText(teacherPhone)
//                etAddress.setText(teacherAddress)
                }
            }
        }
    }

    private fun getTeachInfo(): HashMap<String, String> {
        for (i in 0 until binding.llInfoContainer.childCount) {
            (binding.llInfoContainer[i] as EditText).apply {
                var txt = "fdsfsd"
                if (tag == "teacherPhone") {
                    txt = System.currentTimeMillis().toString().take(10)
                }
                if (tag == "teacherMail") {
                    txt = "$txt@gmail.com"
                }
                if (tag == "teacherBirthday") {
                    txt = (2000..3000).random().toString()
                }
                teachInfoHashMap[tag as String] = txt
            }
        }
        return teachInfoHashMap
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance(teacherProfile: TeacherProfile) =
            TeacherPersonalInfoFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
            }
    }
}