package com.our.app.features.phase_one.teacher.container

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.our.app.MainActivity
import com.our.app.R
import com.our.app.base.BaseFragment
import com.our.app.databinding.FragmentTeacherContainerBinding
import com.our.app.utilities.bindingDelegates.viewBinding
import com.our.data.base.datasources.Prefs
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class TeacherContainerFragment :
    BaseFragment<TeacherContainerViewModel>(R.layout.fragment_teacher_container) {

    private var notificationBody: String = ""

    override val viewModel: TeacherContainerViewModel by viewModels<TeacherContainerViewModelImpl>()
    private val binding by viewBinding(FragmentTeacherContainerBinding::bind)

    @Inject
    lateinit var prefs: Prefs

    private val backPressedCallback = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {}
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        notificationBody = arguments?.getString(ARG_NOTIFICATION_BODY, "") ?: ""
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).onFragmentChanged(this)
        if (prefs.getBoolean(Prefs.COMPLETED_TEACHER_FULL_REGISTRATION)) {
            moveToTeacherLobby()
        } else if (prefs.getBoolean(Prefs.COMPLETED_TEACHER_PERSONAL_INFO_REGISTRATION)) {
            moveToTeacherKnowledge()
        } else {
            moveToPersonalInfo()
        }
    }

    private fun moveToPersonalInfo() {
        findNavController().navigate(
            R.id.action_teacherContainerFragment_to_teacherPersonalInfoFragment
        )
    }

    private fun moveToTeacherKnowledge() {
        findNavController().navigate(
            R.id.action_teacherContainerFragment_to_teacherKnowlageInfoFragment
        )
    }

    private fun moveToTeacherLobby() {
        findNavController().navigate(
            R.id.action_teacherContainerFragment_to_teacherLobbyFragment
        )
    }


    companion object {
        @JvmStatic
        fun newInstance() = TeacherContainerFragment()

        private const val ARG_NOTIFICATION_BODY = "notificationBody"

        fun newInstance(notificationBody: String): TeacherContainerFragment {
            val fragment = TeacherContainerFragment()
            val args = Bundle()
            args.putString(ARG_NOTIFICATION_BODY, notificationBody)
            fragment.arguments = args
            return fragment
        }
    }
}