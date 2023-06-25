package com.our.app.features.phase_one.teacherlobby

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.our.app.R
import com.our.app.base.BaseFragment
import com.our.app.databinding.FragmentTeacherLobbyBinding
import com.our.app.utilities.bindingDelegates.viewBinding
import com.our.data.base.datasources.Prefs
import com.our.domain.features.phase_one.models.local.GotTeacherError
import com.our.domain.features.phase_one.models.local.GotTeacherInfo
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class TeacherLobbyFragment : BaseFragment<TeacherLobbyViewModel>(R.layout.fragment_teacher_lobby) {

    private var notificationBody: String = ""

    companion object {
        private const val ARG_NOTIFICATION_BODY = "notificationBody"

        fun newInstance(notificationBody: String): TeacherLobbyFragment {
            val fragment = TeacherLobbyFragment()
            val args = Bundle()
            args.putString(ARG_NOTIFICATION_BODY, notificationBody)
            fragment.arguments = args
            return fragment
        }
    }



    override val viewModel: TeacherLobbyViewModel by viewModels<TeacherLobbyViewModelImpl>()
    private val binding by viewBinding(FragmentTeacherLobbyBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()

        arguments?.let {
            notificationBody = it.getString(ARG_NOTIFICATION_BODY, "")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnTeacherCreateInfo.setOnClickListener {
            it.isEnabled = false
            viewModel.postTeacherCreateInfo(binding.cvTeacherCreateInfo.getTeachInfo())
        }
    }

    @Inject lateinit var prefs: Prefs

    override fun observeData() {
        super.observeData()
        viewModel.teacherLobbyResponseLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is GotTeacherInfo -> {
                    Toast.makeText(context, prefs.getInt(Prefs.MEMBER_ID).toString(), Toast.LENGTH_SHORT).show()
                }
                is GotTeacherError -> {
                    binding.btnTeacherCreateInfo.isEnabled = true
                }
            }
        }
    }
}