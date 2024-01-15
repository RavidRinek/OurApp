package com.our.app.features.phase_one.student.find_lesson_res

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.our.app.R
import com.our.app.base.BaseFragment
import com.our.app.databinding.FragmentStudentFindLessonResultBinding
import com.our.app.features.phase_one.student.find_lesson.StudentFindLessonFragment
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
    private var mPrice: Int = 0

    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().navigate(R.id.action_studentFindLessonResultFragment_to_studentFindLessonFragment)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            lessonsIds = getIntegerArrayList(StudentFindLessonFragment.SELECTED_SUBJECT_LEVELS_IDS)
            mPrice = getInt(StudentFindLessonFragment.SELECTED_LESSON_MAX_PRICE)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLessons(lessonsIds!!, mPrice)
    }

    override fun observeData() {
        super.observeData()
        viewModel.studentLobbyResponseLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is GotLessons -> {
                    binding.rvLessonResults.adapter = StudentFindLessonResultAdapter(it.lessons,
                        object :
                            StudentFindLessonResultAdapter.OnStudentFindLessonResultAdapterListener {
                            override fun showTeacherProfilerBtnClicked(
                                teacherId: Int,
                                lessonId: Int
                            ) {
                                findNavController().navigate(
                                    R.id.action_studentFindLessonResultFragment_to_teacherProfileFragment,
                                    Bundle().apply {
                                        putInt(SELECTED_LESSON_ID, lessonId)
                                        putInt(SELECTED_TEACHER_ID, teacherId)
                                        putDouble(
                                            StudentFindLessonFragment.SELECTED_LESSON_MAX_PRICE,
                                            mPrice.toDouble()
                                        )
                                        arguments!!.getLong(StudentFindLessonFragment.SELECTED_LESSON_TIME_STAMP)
                                    }
                                )
                            }

                            override fun orderALessonBtnClicked() {}
                        })
                }

                else -> Unit
            }
        }
    }

    companion object {
        const val SELECTED_LESSON_ID = "selected_lesson_id"
        const val SELECTED_TEACHER_ID = "selected_teacher_id"
    }
}