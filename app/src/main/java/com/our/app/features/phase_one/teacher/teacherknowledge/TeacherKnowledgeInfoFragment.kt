package com.our.app.features.phase_one.teacher.teacherknowledge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(
                    R.id.action_teacherKnowlageInfoFragment_to_teacherPersonalInfoFragment
                )
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this, onBackPressedCallback
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

            btnCreateAccount.setOnClickListener {
                var selectedItemLevels = cvTeacherSubjectKnowledge.getSelectedItemLevels()
                var lessonInfo = cvTeacherLessonInfo.getLessonInfoData()
                var degreeInfo = cvTeacherDegreeInfo.getTeacherDegreeDataInfo()
                if (selectedItemLevels.isEmpty()) {
                    selectedItemLevels = HashSet<Int>().apply {
                        add(1)
                    }
                    lessonInfo = TeacherLessonInfoCustomView.LessonInfoData(40, 100, true, "fdsfsd")
                    degreeInfo =
                        TeacherDegreeInfoCustomView.TeacherDegreeDataInfo("fsdfds", "fdsfsdfds")
                }

                val teacherInfo: PostTeacherInfoUseCase.UpdateTeacherInfo =
                    PostTeacherInfoUseCase.UpdateTeacherInfo(
                        teacherId = prefs.getInt(Prefs.MEMBER_ID),
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

                viewModel.completeTeacherFullRegistration(teacherInfo)
            }
        }
    }

    override fun observeData() {
        super.observeData()
        viewModel.teacherLobbyResponseLiveData.observe {
            when (it) {
                GotCompletedFullRegistration -> {
                    prefs.putBoolean(Prefs.COMPLETED_TEACHER_FULL_REGISTRATION, true)
                    findNavController().navigate(
                        R.id.action_teacherKnowlageInfoFragment_to_teacherLobbyFragment
                    )
                }

                is GotSubjectLevelsForTeacherKnowledgeInfo -> {
                    binding.cvTeacherSubjectKnowledge.initViews(it.subjects)
                }

                else -> Unit
            }
        }
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