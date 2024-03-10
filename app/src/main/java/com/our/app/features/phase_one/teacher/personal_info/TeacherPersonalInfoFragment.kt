package com.our.app.features.phase_one.teacher.personal_info

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.RequiresApi
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
import java.lang.Long.getLong
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.ZoneOffset
import java.time.format.DateTimeFormatterBuilder
import java.util.Calendar
import java.util.Date
import kotlin.collections.HashMap
import kotlin.collections.first
import kotlin.collections.set

@AndroidEntryPoint
class TeacherPersonalInfoFragment :
    BaseFragment<TeacherPersonalInfoViewModel>(R.layout.fragment_teacher_personal_info) {

    override val viewModel: TeacherPersonalInfoViewModel by viewModels<TeacherPersonalInfoViewModelImpl>()
    private var _binding: FragmentTeacherPersonalInfoBinding? = null
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.O)
    val formatter = DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd").toFormatter()

//    private var date = ""
    private var fullDate: String = ""

    private val teachInfoHashMap = HashMap<String, String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeacherPersonalInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initViews() {
        binding.etBd.setOnClickListener { showDatePicker() }
        binding.btnTeacherCreateInfo.setOnClickListener {
            val filledInfo = getTeachInfo()
            if (filledInfo.entries.first().value.isNotEmpty()) {
                viewModel.postTeacherCreateInfo(filledInfo)
                viewLifecycleOwner.lifecycleScope.launch {
                    delay(1_500)
                    findNavController().navigate(R.id.action_teacherPersonalInfoFragment_to_teacherKnowlageInfoFragment)
                }
            }
        }

        binding.etBd.setOnClickListener {
            showDatePicker()
        }

//        binding.etBd.text = 'pick date';
//        binding.etBd.text = "בחר תאריך";
    }
    //
//    private fun showDatePickerDialog() {
//        val calendar = Calendar.getInstance()
//        val year = calendar.get(Calendar.YEAR)
//        val month = calendar.get(Calendar.MONTH)
//        val day = calendar.get(Calendar.DAY_OF_MONTH)
//
//        val datePickerDialog = DatePickerDialog(requireContext(),
//            { _, selectedYear, selectedMonth, selectedDay ->
//                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
//                binding.etBd.setText(selectedDate)
//            }, year, month, day)
//
//        datePickerDialog.show()
//
//
//    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                this.fullDate = ""
                val selectedDate = formatDate(year, month, dayOfMonth)

                binding.etBd.hint = selectedDate;

                if(month <= 9 && month >= 0){
                    this.fullDate = "${year}" + "-" + "0" + "${month + 1}" + "-" + "${dayOfMonth}"
                }else{
                    this.fullDate = "${year}" + "-" + "${month + 1}" + "-" + "${dayOfMonth}"
                }
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

//        datePickerDialog.datePicker.maxDate = System.currentTimeMillis() + 1000
        val maxDate = Calendar.getInstance()
        maxDate.add(Calendar.YEAR, -12) // Adding 10 years to the current date
        val maxDateTimestamp = maxDate.timeInMillis
        datePickerDialog.datePicker.maxDate = maxDateTimestamp

        datePickerDialog.show()
        datePickerDialog.setOnDismissListener() {
            formatDateFinal();
            Log.d("test",this.fullDate.toString())
        }
    }

    private fun formatDate(year: Int, month: Int, day: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        return "${day}/${month + 1}/${year}"
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun formatDateFinal(){
        if (this.fullDate != "") {
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            var date: Date? = null
            try {
                date = sdf.parse(fullDate)
            } catch (e: ParseException) {
                // handle exception here !
            }
            val dateFormat = android.text.format.DateFormat.getDateFormat(context)
            val s = date?.time.toString();
            Log.d("test", s);
        }
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
                    etBd.setText(teacherProfile.teacherBirthday)
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
