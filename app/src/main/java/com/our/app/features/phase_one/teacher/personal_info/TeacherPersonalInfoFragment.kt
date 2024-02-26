package com.our.app.features.phase_one.teacher.personal_info

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.view.children
import androidx.core.view.get
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.our.app.R
import com.our.app.base.BaseFragment
import com.our.app.databinding.FragmentTeacherPersonalInfoBinding
import com.our.domain.features.phase_one.models.remote.TeacherProfile
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar

@AndroidEntryPoint
class TeacherPersonalInfoFragment :
    BaseFragment<TeacherPersonalInfoViewModel>(R.layout.fragment_teacher_personal_info) {

    override val viewModel: TeacherPersonalInfoViewModel by viewModels<TeacherPersonalInfoViewModelImpl>()
    private var _binding: FragmentTeacherPersonalInfoBinding? = null
    private val binding get() = _binding!!

    private val teachInfoHashMap = HashMap<String, String>()
    private var isbtnTeacherCreateInfoClickable = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeacherPersonalInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        updateCreateInfoBtnState()
        binding.btnTeacherCreateInfo.setOnClickListener {
            if (isbtnTeacherCreateInfoClickable) {
                val filledInfo = getTeachInfo()
                if (filledInfo.entries.first().value.isNotEmpty()) {
                    viewModel.postTeacherCreateInfo(filledInfo)
                    viewLifecycleOwner.lifecycleScope.launch {
                        delay(1_500)
                        findNavController().navigate(R.id.action_teacherPersonalInfoFragment_to_teacherKnowlageInfoFragment)
                    }
                }
            }
        }

        binding.llInfoContainer.children.forEach {
            (it as? EditText)?.addTextChangedListener { updateCreateInfoBtnState() }
        }

        binding.etBd.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun updateCreateInfoBtnState() {
        binding.apply {
            if (etName.text.isEmpty()
                || etLastName.text.isEmpty()
                || etPhone.text.isEmpty()
                || etMail.text.isEmpty()
                || etBd.text.isEmpty()
                || etAddress.text.isEmpty()
            ) {
                btnTeacherCreateInfo.apply {
                    setBackgroundResource(R.drawable.rec_767676_rad_12)
                    isbtnTeacherCreateInfoClickable = false
                }
            } else {
                btnTeacherCreateInfo.apply {
                    setBackgroundResource(R.drawable.rec_ff3817_rad_12)
                    isbtnTeacherCreateInfoClickable = true
                }
            }
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                binding.etBd.setText(selectedDate)
                updateCreateInfoBtnState()
            }, year, month, day
        )

        datePickerDialog.show()
    }

    override fun observeData() {
        super.observeData()
        viewModel.teacherPersonalInfoResponseLiveData.observe(viewLifecycleOwner) { response ->
            response?.teacherProfile?.let { teacherProfile ->
                binding.apply {
                    etName.setText(teacherProfile.teacherName)
                    etLastName.setText(teacherProfile.teacherLastName)
                    etPhone.setText(teacherProfile.teacherPhone)
                    etMail.setText(teacherProfile.teacherMail)
//                    etBd.setText(teacherProfile.teacherBirthday)
                    // etAddress.setText(teacherProfile.teacherAddress)
                }
            }
        }
    }

    private fun getTeachInfo(): HashMap<String, String> {
        val teachInfoHashMap = HashMap<String, String>()
        for (i in 0 until binding.llInfoContainer.childCount) {
            val editText = binding.llInfoContainer.getChildAt(i) as? EditText
            editText?.let {
                val tagString = it.tag as? String
                var txt = it.text.toString()
                txt = if (txt.isEmpty()) {
                    when (tagString) {
                        "teacherPhone" -> System.currentTimeMillis().toString().take(10)
                        "teacherMail" -> "$txt@gmail.com"
                        "teacherBirthday" -> (2000..3000).random().toString()
                        else -> "Unit"
                    }
                } else txt
                teachInfoHashMap[tagString ?: ""] = txt
            }
        }
        return teachInfoHashMap
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance(teacherProfile: TeacherProfile) =
            TeacherPersonalInfoFragment().apply {
                // Handle the newInstance logic if needed
            }
    }
}
