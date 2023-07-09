package com.our.app.features.phase_one.teacherlobby

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.clearFragmentResultListener
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.our.app.R
import com.our.app.base.BaseFragment
import com.our.app.databinding.FragmentTeacherLobbyBinding
import com.our.app.features.phase_one.teacherlobby.teacherknowledge.TeacherKnowledgeInfoFragment
import com.our.app.utilities.bindingDelegates.viewBinding
import com.our.data.base.datasources.Prefs
import com.our.domain.features.phase_one.models.local.GotFcmToken
import com.our.domain.features.phase_one.models.local.GotSubjectLevelsForTeacherKnowledgeInfo
import com.our.domain.features.phase_one.models.local.GotTeacherError
import com.our.domain.features.phase_one.models.local.GotFirstPageInfo
import com.our.domain.features.phase_one.models.local.GotTeacherUpcomingLessons
import com.our.domain.features.phase_one.models.local.FirstTeacherDetails
import com.our.domain.features.phase_one.usecases.PostTeacherInfoUseCase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class TeacherLobbyFragment : BaseFragment<TeacherLobbyViewModel>(R.layout.fragment_teacher_lobby) {

    override val viewModel: TeacherLobbyViewModel by viewModels<TeacherLobbyViewModelImpl>()
    private val binding by viewBinding(FragmentTeacherLobbyBinding::bind)

    @Inject
    lateinit var prefs: Prefs

    private var cameFromPopBackStack: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        when {
            prefs.getBoolean(Prefs.COMPLETED_TEACHER_REGISTRATION) -> {

            }

            !cameFromPopBackStack && prefs.contains(Prefs.MEMBER_ID) -> {
                viewModel.getTeacherById(prefs.getInt(Prefs.MEMBER_ID))
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

        setFragmentResultListener(K_TEACHER_INFO_LISTENER) { _, bundle ->
            clearFragmentResultListener(requestKey = K_TEACHER_INFO_LISTENER)
            val teacherInfo =
                bundle.getParcelable<PostTeacherInfoUseCase.UpdateTeacherInfo>(K_TEACHER_INFO_DATA)
            Handler(Looper.getMainLooper()).post {
                teacherInfo?.let { viewModel.postTeacherInfo(it) }
            }
        }
    }

    private fun initViews() {
        binding.btnTeacherCreateInfo.setOnClickListener {
            val a = binding.cvTeacherCreateInfo.getTeachInfo()
            if (a.entries.first().value.isNotEmpty()) {
                it.isEnabled = false
                viewModel.postTeacherCreateInfo(a)
            }
        }
    }

    override fun observeData() {
        super.observeData()
        viewModel.teacherLobbyResponseLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is GotFirstPageInfo -> {
                    binding.cvTeacherCreateInfo.initViews(it.teacherProfile)
                    viewModel.getSubjectLevelsForTeacherKnowlageInfo()
                }

                is GotSubjectLevelsForTeacherKnowledgeInfo -> {
                    findNavController().navigate(
                        R.id.action_teacherLobbyFragment_to_teacherKnowlageInfoFragment,
                        Bundle().apply {
                            putSerializable(
                                TeacherKnowledgeInfoFragment.K_SUBJECTS,
                                ArrayList(it.subjects)
                            )
                        }
                    )
                }

                is FirstTeacherDetails -> {
                    prefs.putBooleanAsync(Prefs.COMPLETED_TEACHER_REGISTRATION, true)
                    binding.groupCreateTeacher.visibility = View.GONE
                }

                is GotTeacherUpcomingLessons -> {
                    binding.cvTeacherLobbyEmptyState.initViews(
                        lessons = it.lessons,
                        listener = object :
                            TeacherRegisteredLobbyCv.OnTeacherLobbyEmptyStateCustomViewListener {
                            override fun updateAvailability(status: Boolean) {
                                Toast.makeText(
                                    requireContext(),
                                    "updateAvailability: status: $status",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })
                }

                is GotTeacherError -> {
                    Toast.makeText(requireContext(), "GotTeacherError", Toast.LENGTH_SHORT).show()
                    binding.btnTeacherCreateInfo.isEnabled = true
                }

                GotFcmToken -> TODO()
            }
        }
    }

    companion object {
        const val K_TEACHER_INFO_LISTENER = "teacherInfo_listener"
        const val K_TEACHER_INFO_DATA = "teacherInfo_data"

        @JvmStatic
        fun newInstance() = TeacherLobbyFragment()
    }
}