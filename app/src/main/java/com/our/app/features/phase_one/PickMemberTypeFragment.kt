package com.our.app.features.phase_one

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.our.app.R
import com.our.app.databinding.FragmentPickMemberTypeBinding
import com.our.app.utilities.bindingDelegates.viewBinding


class PickMemberTypeFragment : Fragment() {

    private val binding by viewBinding(FragmentPickMemberTypeBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pick_member_type, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnStudent.setOnClickListener {
            findNavController().navigate(R.id.action_pickMemberTypeFragment_to_studentLobbyFragment)
        }

        binding.btnTeacher.setOnClickListener {
            findNavController().navigate(R.id.action_pickMemberTypeFragment_to_teacherContainerFragment)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = PickMemberTypeFragment()
    }
}