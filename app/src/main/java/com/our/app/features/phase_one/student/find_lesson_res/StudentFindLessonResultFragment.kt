package com.our.app.features.phase_one.student.find_lesson_res

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.our.app.R
import com.our.app.base.BaseFragment
import com.our.app.databinding.FragmentStudentFindLessonResultBinding
import com.our.app.features.phase_one.student.find_lesson.StudentFindLessonViewModel
import com.our.app.features.phase_one.student.find_lesson.StudentFindLessonViewModelImpl
import com.our.app.features.phase_one.student.student_lobby.StudentLobbyViewModel
import com.our.app.features.phase_one.student.student_lobby.StudentLobbyViewModelImpl
import com.our.app.utilities.bindingDelegates.viewBinding
import com.our.domain.features.phase_one.models.local.GotLessons
import com.our.domain.features.phase_one.models.remote.Lesson
import dagger.hilt.android.AndroidEntryPoint

const val LESSONS_IDS: String = "lessons_ids"

@AndroidEntryPoint
class StudentFindLessonResultFragment :
    BaseFragment<StudentFindLessonResultViewModel>(R.layout.fragment_student_find_lesson_result) {

    override val viewModel: StudentFindLessonResultViewModel by viewModels<StudentFindLessonResultViewModelImpl>()
    private val binding by viewBinding(FragmentStudentFindLessonResultBinding::bind)

    private var lessonsIds: ArrayList<Int>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            lessonsIds = getIntegerArrayList("TAHAT")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLessons(lessonsIds!!)
    }

    override fun observeData() {
        super.observeData()
        viewModel.studentLobbyResponseLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is GotLessons -> {
                    val mappedLessons = it.lessons.map { it.lesson }
                    binding.rvLessonResults.adapter = StudentFindLessonResultAdapter(mappedLessons,
                        object :
                            StudentFindLessonResultAdapter.OnStudentFindLessonResultAdapterListener {
                            override fun showTeacherProfilerBtnClicked(
                                teacherId: Int,
                                lessonId: Int
                            ) {
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

                else -> Unit
            }
        }
    }
}