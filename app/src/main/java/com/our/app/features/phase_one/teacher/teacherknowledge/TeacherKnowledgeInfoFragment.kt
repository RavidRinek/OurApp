package com.our.app.features.phase_one.teacher.teacherknowledge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.our.app.R
import com.our.app.base.BaseFragment
import com.our.app.databinding.FragmentTeacherKnowlageInfoBinding
import com.our.data.base.datasources.Prefs
import com.our.domain.features.phase_one.models.local.GotCompletedFullRegistration
import com.our.domain.features.phase_one.models.local.GotSubjectLevelsForTeacherKnowledgeInfo
import com.our.domain.features.phase_one.usecases.PostTeacherInfoUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TeacherKnowledgeInfoFragment :
    BaseFragment<TeacherKnowledgeViewModel>(R.layout.fragment_teacher_knowlage_info) {

    override val viewModel: TeacherKnowledgeViewModel by viewModels<TeacherKnowledgeViewModelImpl>()

    private var _binding: FragmentTeacherKnowlageInfoBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var prefs: Prefs

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().navigate(
                R.id.action_teacherKnowlageInfoFragment_to_teacherPersonalInfoFragment
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeacherKnowlageInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            llInfoContainer.setOnClickListener {
                cvTeacherSubjectKnowledge.dismissRvSubjectsVisibility()
                cvTeacherLessonInfo.dismissContainer()
                cvTeacherDegreeInfo.dismissContainer()
            }

            cvTeacherSubjectKnowledge.setListener(object :
                TeacherSubjectKnowledgeCustomView.Listener {
                override fun updateSelectedState(idsListEmpty: Boolean) {

                }
            })
            btnCreateAccount.setOnClickListener {
                var selectedItemLevels = cvTeacherSubjectKnowledge.getSelectedItemLevels()
                var lessonInfo = cvTeacherLessonInfo.getLessonInfoData()
                var degreeInfo = cvTeacherDegreeInfo.getTeacherDegreeDataInfo()

                if (selectedItemLevels.isEmpty()) {
                    if (showReasonForDisabledBtn()){
                        return@setOnClickListener
                    }
//                    selectedItemLevels = getMockLevelIds()
//                    lessonInfo = getMockLessonInfoData()
//                    degreeInfo = getMockTeacherDegreeDataInfo()
                }

                val teacherInfo = prepTeacherKnowledgeToPostServer(
                    selectedItemLevels,
                    lessonInfo,
                    degreeInfo
                )

                viewModel.completeTeacherFullRegistration(teacherInfo)
            }
        }
    }

    private fun showReasonForDisabledBtn(): Boolean {
        val lessonData = binding.cvTeacherLessonInfo.getLessonInfoData()
        val reason = when {
            binding.cvTeacherSubjectKnowledge.getSelectedItemLevels()
                .isEmpty() -> "Must pick at least one Subject"

            lessonData.pricePer60m == 0 -> "Set a price for 60m lesson"
            lessonData.pricePer40m == 0 -> "Set a price for 40m lesson"
            else -> ""
        }
        if (reason.isNotEmpty()) {
            Toast.makeText(requireContext(), reason, Toast.LENGTH_LONG).show()
            return true
        } else {
            return false
        }
    }

    override fun observeData() {
        super.observeData()
        viewModel.teacherLobbyResponseLiveData.observe {
            when (it) {
                is GotSubjectLevelsForTeacherKnowledgeInfo -> {
                    binding.cvTeacherSubjectKnowledge.initViews(it.subjects)
                }

                GotCompletedFullRegistration -> {
                    findNavController().navigate(
                        R.id.action_teacherKnowlageInfoFragment_to_teacherLobbyFragment
                    )
                }

                else -> Unit
            }
        }
    }

    private fun getMockLevelIds(): ArrayList<Int> {
        return ArrayList<Int>().apply {
            add(1)
        }
    }

    private fun getMockLessonInfoData(): TeacherLessonInfoCustomView.LessonInfoData {
        return TeacherLessonInfoCustomView
            .LessonInfoData(
                40,
                100,
                true,
                "fdsfsd"
            )
    }

    private fun getMockTeacherDegreeDataInfo(): TeacherDegreeInfoCustomView.TeacherDegreeDataInfo {
        return TeacherDegreeInfoCustomView
            .TeacherDegreeDataInfo(
                "fsdfds",
                "fdsfsdfds"
            )
    }

    private fun prepTeacherKnowledgeToPostServer(
        selectedItemLevels: ArrayList<Int>,
        lessonInfo: TeacherLessonInfoCustomView.LessonInfoData,
        degreeInfo: TeacherDegreeInfoCustomView.TeacherDegreeDataInfo
    ): PostTeacherInfoUseCase.UpdateTeacherInfo {
        return PostTeacherInfoUseCase.UpdateTeacherInfo(
            teacherId = prefs.getInt(Prefs.TEACHER_ID),
            teacherSubjectsLevelsId = selectedItemLevels.toList(),
            lessonInfo = PostTeacherInfoUseCase.LessonInfo(
                pricePer60m = lessonInfo.pricePer60m,
                pricePer40m = lessonInfo.pricePer40m,
                firstLessonFree = lessonInfo.allowFreeFirstLesson,
                additionalInfo = lessonInfo.additionalInfo
            ),
            degreeInfo = PostTeacherInfoUseCase.DegreeInfo(
                schoolName = degreeInfo.schoolName,
                degreeName = degreeInfo.degreeName
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val K_SUBJECTS = "subjectsKnowledge"

        @JvmStatic
        fun newInstance() =
            TeacherKnowledgeInfoFragment()
    }
}