package com.our.app.features.phase_one.teacherlobby

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.our.app.R
import com.our.app.base.BaseFragment
import com.our.app.databinding.FragmentTearcherProfileBinding
import com.our.app.utilities.bindingDelegates.viewBinding
import com.our.app.utilities.extensions.loadImage
import com.our.domain.features.phase_one.models.local.GotFirstPageInfo
import com.our.domain.features.phase_one.models.remote.TeacherProfile
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeacherProfileFragment :
    BaseFragment<TeacherLobbyViewModel>(R.layout.fragment_tearcher_profile) {

    override val viewModel: TeacherLobbyViewModel by viewModels<TeacherLobbyViewModelImpl>()
    private val binding by viewBinding(FragmentTearcherProfileBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.apply {
            viewModel.getTeacherById(getInt("teacherId"))
        }
    }

    override fun observeData() {
        super.observeData()
        viewModel.teacherLobbyResponseLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is GotFirstPageInfo -> handleTeacherProfileResponse(it.teacherProfile)
                else -> Unit
            }
        }
    }

    private fun handleTeacherProfileResponse(teacherProfile: TeacherProfile) {
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