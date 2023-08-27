package com.our.app.features.phase_one.studentlobby

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.our.app.R
import com.our.app.base.BaseFragment
import com.our.app.databinding.FragmentStudentFindLessonResultBinding
import com.our.app.utilities.bindingDelegates.viewBinding
import com.our.domain.features.phase_one.models.remote.Lesson
import dagger.hilt.android.AndroidEntryPoint

const val LESSONS: String = "lessons"

@AndroidEntryPoint
class StudentFindLessonResultFragment :
    BaseFragment<StudentLobbyViewModel>(R.layout.fragment_student_find_lesson_result) {

    override val viewModel: StudentLobbyViewModel by viewModels<StudentLobbyViewModelImpl>()
    private val binding by viewBinding(FragmentStudentFindLessonResultBinding::bind)
    private var lessons: List<Lesson>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            lessons = getParcelableArrayList(LESSONS)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lessons?.let {
            binding.rvLessonResults.adapter = StudentFindLessonResultAdapter(it,
                object : StudentFindLessonResultAdapter.OnStudentFindLessonResultAdapterListener {
                    override fun showTeacherProfilerBtnClicked(teacherId: Int, lessonId: Int) {
                        findNavController().navigate(
                            R.id.action_studentFindLessonResultFragment_to_teacherProfileFragment,
                            Bundle().apply {
                                putInt("lessonId", lessonId)
                                putInt("teacherId", teacherId)
                            }
                        )
                    }

                    override fun orderALessonBtnClicked() {
                    }
                })
        }
    }
}