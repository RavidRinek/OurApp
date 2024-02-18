package com.our.app.features.phase_one.student.find_lesson

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.our.app.R
import com.our.app.base.BaseFragment
import com.our.app.databinding.FragmentStudentFindLessonBinding
import com.our.domain.features.phase_one.models.local.GotSubjects
import com.our.domain.features.phase_one.models.remote.BaseSubject
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatterBuilder
import java.util.Calendar


@AndroidEntryPoint
class StudentFindLessonFragment :
    BaseFragment<StudentFindLessonViewModel>(R.layout.fragment_student_find_lesson) {

    override val viewModel: StudentFindLessonViewModel by viewModels<StudentFindLessonViewModelImpl>()
    private var _binding: FragmentStudentFindLessonBinding? = null
    private val binding get() = _binding!!

    private var date = ""
    private var time = ""
    private var fullDate: Any = ""

    @RequiresApi(Build.VERSION_CODES.O)
    val formatter = DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH:mm").toFormatter()

    private val backPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentFindLessonBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        viewModel.getSubjects()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initViews() {
        binding.clContainer.setOnClickListener { binding.subjectSpinnerMain.dismissRvSubjectsVisibility() }
        binding.layoutLessonTime.tvLessonDate.setOnClickListener { showTimePickerDialog() }
        binding.layoutLessonDate.tvLessonDate.setOnClickListener { showDatePicker() }
        binding.btnStudentFindLesson.setOnClickListener {
            val selectedItemLevels = binding.subjectSpinnerMain.getSelectedItemLevels().ifEmpty {
                ArrayList<Int>().apply {
                    add(4)
                    add(6)
                    add(7)
                }
            }

            findNavController().navigate(
                R.id.action_studentFindLessonFragment_to_studentFindLessonResultFragment,
                Bundle().apply {
                    putIntegerArrayList(
                        SELECTED_SUBJECT_LEVELS_IDS,
                        selectedItemLevels
                    )
                    putLong(SELECTED_LESSON_TIME_STAMP, 5984736L)
                    putInt(SELECTED_LESSON_MAX_PRICE, binding.cvLessonPrice.pickedPrice)
                }
            )
        }

        binding.layoutLessonTime.tvLessonDate.text = "בחר שעה"
        binding.layoutLessonTime.tvLessonDateTitle.text = "בחר שעה"

        binding.layoutLessonDate.tvLessonDate.text = "בחר תאריך"
        binding.layoutLessonDate.tvLessonDateTitle.text = "בחר תאריך"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showTimePickerDialog() {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR)
        val minute = c.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(),

            { _, hourOfDay, minute ->
                this.time = ""
                if (hourOfDay <= 9)
                    this.time = this.time + "0" + "$hourOfDay:"
                else this.time = this.time + "$hourOfDay:"
                if (minute <= 9)
                    this.time = this.time + "0" + "$minute"
                else
                    this.time = this.time + "$minute"

                binding.layoutLessonTime.tvLessonDate.text = this.time
                println(this.fullDate);
                Log.d("test",this.fullDate.toString())
            },
            hour,
            minute,
            true // 24-hour format
        )

        timePickerDialog.show()
        timePickerDialog.setOnDismissListener(){
            formatDateFinal();
            Log.d("test",this.fullDate.toString())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                this.date = ""
                val selectedDate = formatDate(year, month, dayOfMonth)
                binding.layoutLessonDate.tvLessonDate.text = selectedDate

                if(month <= 9 && month >= 0){
                    this.date = "${year}" + "-" + "0" + "${month + 1}" + "-" + "${dayOfMonth}"
                }else{
                    this.date = "${year}" + "-" + "${month + 1}" + "-" + "${dayOfMonth}"
                }
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
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

    override fun observeData() {
        super.observeData()
        viewModel.studentLobbyResponseLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is GotSubjects -> initSpinnerMain(binding.subjectSpinnerMain, it.subjects)
                else -> Unit
            }
        }
    }

    private fun initSpinnerMain(
        subjectSpinnerCustomView: StudentSubjectSpinnerCustomView,
        subjects: List<BaseSubject>
    ) {
        val subjectMode = if (subjectSpinnerCustomView.id == binding.subjectSpinnerMain.id)
            SubjectsSpinnerAdapter.SubjectMode.MAIN
        else SubjectsSpinnerAdapter.SubjectMode.BRANCH

        subjectSpinnerCustomView.setSubjectsItems(subjectMode, subjects)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun formatDateFinal(){
        if (this.date != "" && this.time != "") {
            fullDate = LocalDateTime.parse(this.date + " " + this.time, formatter)
                .toEpochSecond(ZoneOffset.UTC).toString()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val SELECTED_SUBJECT_LEVELS_IDS = "selected_subject_lvl_ids"
        const val SELECTED_LESSON_TIME_STAMP = "selected_lesson_timestamp"
        const val SELECTED_LESSON_MAX_PRICE = "selected_lesson_max_price"

        @JvmStatic
        fun newInstance() = StudentFindLessonFragment()
    }
}