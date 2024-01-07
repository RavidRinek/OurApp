package com.our.app.features.phase_one.student.find_lesson

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.our.app.R
import com.our.app.base.BaseFragment
import com.our.app.databinding.FragmentStudentFindLessonBinding
import com.our.domain.features.phase_one.models.local.GotSubjects
import com.our.domain.features.phase_one.models.remote.BaseSubject
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class StudentFindLessonFragment :
    BaseFragment<StudentFindLessonViewModel>(R.layout.fragment_student_find_lesson) {

    override val viewModel: StudentFindLessonViewModel by viewModels<StudentFindLessonViewModelImpl>()
    private var _binding: FragmentStudentFindLessonBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentFindLessonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        viewModel.getSubjects()
    }

    private fun initViews() {
        binding.clContainer.setOnClickListener {
            binding.subjectSpinnerMain.dismissRvSubjectsVisibility()
        }
        binding.btnStudentFindLesson.setOnClickListener {
      //todo moved subjects IDS to 'StudentFindLessonResultFragment
        }
    }

    override fun observeData() {
        super.observeData()
        viewModel.studentLobbyResponseLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is GotSubjects -> initSpinnerMain(binding.subjectSpinnerMain, it.subjects)
                else -> Unit
            }
        }
    }

    private fun initSpinnerMain(
        subjectSpinnerCustomView: StudentSubjectSpinnerCustomView,
        subjects: List<BaseSubject>
    ) {
        val subjectMode = if (subjectSpinnerCustomView.id == binding.subjectSpinnerMain.id)
            SubjectsSpinnerAdapter.SubjectMode.MAIN
        else SubjectsSpinnerAdapter.SubjectMode.BRANCH

        subjectSpinnerCustomView.setSubjectsItems(subjectMode, subjects,
            object : SubjectsSpinnerAdapter.OnSubjectsSpinnerAdapterListener {
                override fun itemClicked(baseSubject: BaseSubject) {
                    when (subjectMode) {
                        SubjectsSpinnerAdapter.SubjectMode.MAIN -> {
                            viewModel.getSubjectBranches(baseSubject.id)
                        }

                        SubjectsSpinnerAdapter.SubjectMode.BRANCH -> {
                            viewModel.getSubjectBranches(baseSubject.id)
                        }
                    }
                }

                override fun itemClicked(lessonId: Int) {

                }
            })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = StudentFindLessonFragment()
    }
}