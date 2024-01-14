package com.our.app.features.phase_one.teacher.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.our.app.R
import com.our.app.base.BaseFragment
import com.our.app.databinding.FragmentTearcherProfileBinding
import com.our.app.features.phase_one.student.order_lesson.OrderLessonFragment
import com.our.app.features.phase_one.student.order_lesson.OrderLessonUi
import com.our.app.features.phase_one.teacher.container.TeacherContainerViewModel
import com.our.app.features.phase_one.teacher.container.TeacherContainerViewModelImpl
import com.our.app.utilities.bindingDelegates.viewBinding
import com.our.app.utilities.extensions.loadImage
import com.our.domain.features.phase_one.models.local.GotTeacherPersonalInfo
import com.our.domain.features.phase_one.models.remote.TeacherProfile
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeacherProfileFragment :
    BaseFragment<TeacherContainerViewModel>(R.layout.fragment_tearcher_profile) {

    override val viewModel: TeacherContainerViewModel by viewModels<TeacherContainerViewModelImpl>()
    private val binding by viewBinding(FragmentTearcherProfileBinding::bind)

    private lateinit var teacherProfile: TeacherProfile

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.apply {
            viewModel.getTeacherById(getInt("teacherId"))
        }
        binding.btnStudentFindLesson.setOnClickListener {
            findNavController().navigate(
                R.id.action_teacherProfileFragment_to_orderLessonFragment,
                Bundle().apply {
                    putParcelable(
                        OrderLessonFragment.K_LESSON_INFO, OrderLessonUi(
                            lessonId = "fsdf",
                            price = "fdsfsd",
                            name = "fdsffdsfsddfs",
                            time = "fdsfsdfsdfdsfsddf"
                        )
                    )
                    putInt("lessonId", arguments?.getInt("lessonId") ?: 0)
                }
            )
        }
    }

    override fun observeData() {
        super.observeData()
        viewModel.teacherLobbyResponseLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is GotTeacherPersonalInfo -> handleTeacherProfileResponse(it.teacherProfile)
                else -> Unit
            }
        }
    }

    private fun handleTeacherProfileResponse(teacherProfile: TeacherProfile) {
        this.teacherProfile = teacherProfile
        teacherProfile.apply {
            binding.apply {
                ivTeacherAvatar.loadImage(teacherAvatar)
                tvTeacherName.text = teacherName
                rvTeacherSubjects.adapter = TeacherProfileSubjectsAdapter().also {
                    it.submitList(teacherProfile.subjects)
                }
                rvTeacherPhotos.adapter = TeacherProfileGalleryAdapter().also {
                    it.submitList(teacherProfile.profileGallery)
                }
                rvTeacherReviews.adapter = TeacherProfileReviewsAdapter().also {
                    println()
                    it.submitList(teacherProfile.reviews)
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            TeacherProfileFragment()
    }
}