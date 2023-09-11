package com.our.app.features.phase_one.teacherlobby.teacher_lobby

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.our.app.R
import com.our.app.base.BaseFragment
import com.our.app.databinding.FragmentTeacherLobbyBinding
import com.our.app.features.phase_one.teacherlobby.orders.TeacherOrdersFragment
import com.our.app.utilities.bindingDelegates.viewBinding
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


@AndroidEntryPoint
class TeacherLobbyFragment :
    BaseFragment<TeacherLobbyViewModel>(R.layout.fragment_teacher_lobby) {

    override val viewModel: TeacherLobbyViewModel by viewModels<TeacherLobbyViewModelImpl>()
    private val binding by viewBinding(FragmentTeacherLobbyBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        viewModel.getTeacherUpcomingLessons()
    }

    private fun initViews() {
        binding.switchAvailableForLesson.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(
                requireContext(),
                "updateAvailability: status: $isChecked",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun observeData() {
        super.observeData()
        viewModel.teacherLobbyResponseLiveData.observe(viewLifecycleOwner) {
            when (it.emitType) {
                EmitType.NONE -> {
                    println("teacherLobbyResponseLiveData: emitType: NONE")
                }

                EmitType.TEACHER_ORDERS -> {
                    println("teacherLobbyResponseLiveData: emitType: TEACHER_ORDERS")
                    binding.rvTeacherLessons.adapter =
                        TeacherUpcomingLessonsAdapter(it.uiState.teacherOrders)
/*
                                        findNavController().navigate(
                                            R.id.action_teacherLobbyyFragment_to_teacherOrdersFragment,
                                            Bundle().apply {
                                                putSerializable(
                                                    TeacherOrdersFragment.K_ORDERS,
                                                    ArrayList(it.uiState.teacherOrders)
                                                )
                                            }
                                        )
*/
                }
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TeacherLobbyFragment()
    }
}