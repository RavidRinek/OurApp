package com.our.app.features.phase_one.studentlobby

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.our.app.R
import com.our.app.base.BaseFragment
import com.our.app.databinding.FragmentStudentLobbyBinding
import com.our.app.utilities.bindingDelegates.viewBinding
import com.our.domain.features.phase_one.models.local.GotLessons
import com.our.domain.features.phase_one.models.local.GotSubjectBranches
import com.our.domain.features.phase_one.models.local.GotSubjects
import com.our.domain.features.phase_one.models.remote.BaseSubject
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudentLobbyFragment : BaseFragment<StudentLobbyViewModel>(R.layout.fragment_student_lobby) {

    override val viewModel: StudentLobbyViewModel by viewModels<StudentLobbyViewModelImpl>()
    private val binding by viewBinding(FragmentStudentLobbyBinding::bind)
    private var subjectBranchId: Int = 42

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.clContainer.setOnClickListener {
            binding.subjectSpinnerMain.dismissRvSubjectsVisibility()
            binding.subjectSpinnerBranch.dismissRvSubjectsVisibility()
        }
        binding.btnStudentFindLesson.setOnClickListener {
            viewModel.getLessons(subjectBranchId)
        }
        viewModel.getSubjects()
    }

    override fun observeData() {
        super.observeData()
        viewModel.studentLobbyResponseLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is GotSubjects ->
                    initSpinnerMain(
                        binding.subjectSpinnerMain,
                        it.subjects
                    )
                is GotSubjectBranches ->
                    initSpinnerMain(
                        binding.subjectSpinnerBranch,
                        it.subjectBranches
                    )
                is GotLessons -> {
                    val bundle = Bundle()
                    bundle.putParcelableArrayList(LESSONS, ArrayList(it.lessons))
                    findNavController().navigate(
                        R.id.action_studentLobbyFragment_to_studentFindLessonResultFragment,
                        bundle
                    )
                }
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
            })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StudentLobbyFragment()
    }
}