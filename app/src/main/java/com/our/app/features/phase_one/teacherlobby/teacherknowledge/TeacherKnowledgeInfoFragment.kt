package com.our.app.features.phase_one.teacherlobby.teacherknowledge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.our.app.databinding.FragmentTeacherKnowlageInfoBinding
import com.our.app.features.phase_one.teacherlobby.container.TeacherContainerFragment
import com.our.data.base.datasources.Prefs
import com.our.domain.features.phase_one.models.remote.Subject
import com.our.domain.features.phase_one.usecases.PostTeacherInfoUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TeacherKnowledgeInfoFragment : Fragment() {

    private var _binding: FragmentTeacherKnowlageInfoBinding? = null
    private val binding get() = _binding!!
    private var subjects: List<Subject>? = null

    @Inject
    lateinit var prefs: Prefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            subjects = it.getSerializable(K_SUBJECTS) as List<Subject>
        }
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
            cvTeacherSubjectKnowledge.initViews(subjects!!)

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
                    lessonInfo = TeacherLessonInfoCustomView.LessonInfoData(40,100,true,"fdsfsd")
                    degreeInfo= TeacherDegreeInfoCustomView.TeacherDegreeDataInfo("fsdfds", "fdsfsdfds")
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


                setFragmentResult(
                    TeacherContainerFragment.K_TEACHER_INFO_LISTENER,
                    bundleOf(TeacherContainerFragment.K_TEACHER_INFO_DATA to teacherInfo)
                )
                findNavController().popBackStack()
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
        fun newInstance(param1: ArrayList<Subject>) =
            TeacherKnowledgeInfoFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(K_SUBJECTS, param1)
                }
            }
    }
}