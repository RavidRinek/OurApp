package com.our.app.features.phase_one.studentlobby

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.our.app.R
import com.our.app.base.BaseFragment
import com.our.app.databinding.FragmentStudentLobbyBinding
import com.our.app.utilities.bindingDelegates.viewBinding
import com.our.domain.features.phase_one.models.local.GotLessons
import com.our.domain.features.phase_one.models.local.GotSubjectBranches
import com.our.domain.features.phase_one.models.local.GotSubjects
import com.our.domain.features.phase_one.models.remote.BaseSubject
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatterBuilder
import java.util.Calendar

@AndroidEntryPoint
class StudentLobbyFragment : BaseFragment<StudentLobbyViewModel>(R.layout.fragment_student_lobby) {

    override val viewModel: StudentLobbyViewModel by viewModels<StudentLobbyViewModelImpl>()
    private val binding by viewBinding(FragmentStudentLobbyBinding::bind)
    private var subjectBranchId: Int = 42
    private var date = "";
    private var time = "";
    private var fullDate: Any = "";

    @RequiresApi(Build.VERSION_CODES.O)
    val formatter = DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH:mm").toFormatter()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.clContainer.setOnClickListener {
            binding.subjectSpinnerMain.dismissRvSubjectsVisibility()
        }
        binding.btnStudentFindLesson.setOnClickListener {
            viewModel.getLessons(binding.subjectSpinnerMain.listOfPickedBranchLevels)
        }

        binding.layoutLessonTime.tvLessonDate.setOnClickListener {
            showTimePickerDialog()
        }

        binding.layoutLessonDate.tvLessonDate.setOnClickListener {
            showDatePicker()
        }

        viewModel.getSubjects()

        //fix initial values of TimePickingSection (included XML)
        binding.layoutLessonTime.tvLessonDate.setText("בחר שעה")
        binding.layoutLessonTime.tvLessonDateTitle.setText("בחר שעה")

        //fix initial values of DatePickingSection (included XML)
        binding.layoutLessonDate.tvLessonDate.setText("בחר תאריך")
        binding.layoutLessonDate.tvLessonDateTitle.setText("בחר תאריך")
    }

    override fun observeData() {
        super.observeData()
        viewModel.studentLobbyResponseLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is GotSubjects ->
                    initSpinnerMain(
                        binding.subjectSpinnerMain,
                        it.subjects
                    )
                is GotLessons -> {
                    val bundle = Bundle()
                    val a = it.lessons.map { it.lesson }
                    bundle.putParcelableArrayList(LESSONS, ArrayList(a))
                    findNavController().navigate(
                        R.id.action_studentLobbyFragment_to_studentFindLessonResultFragment,
                        bundle
                    )
                }
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

        subjectSpinnerCustomView.setSubjectsItems(subjectMode, subjects,
            object : SubjectsSpinnerAdapter.OnSubjectsSpinnerAdapterListener {
                override fun itemClicked(baseSubject: BaseSubject) {
                    when (subjectMode) {
                        SubjectsSpinnerAdapter.SubjectMode.MAIN -> {
                            viewModel.getSubjectBranches(baseSubject.id)
                        }
                        SubjectsSpinnerAdapter.SubjectMode.BRANCH -> {
                            viewModel.getSubjectBranches(baseSubject.id)
                        }
                    }
                }

                override fun itemClicked(lessonId: Int) {
                    // Handle item click if needed
                }
            })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showTimePickerDialog() {

        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR)
        val minute = c.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(),

            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                // Handle selected time (hourOfDay and minute)
                this.time="";
                if (hourOfDay <= 9)
                    this.time= this.time + "0"+"$hourOfDay:"
                   else   this.time= this.time + "$hourOfDay:"
                if (minute <= 9)
                    this.time= this.time + "0"+"$minute"
                else
                    this.time= this.time + "$minute"

                binding.layoutLessonTime.tvLessonDate.setText(this.time)

            },
            hour,
            minute,
            true // 24-hour format
        )


        timePickerDialog.show()
        if(this.date != "" && this.time != ""){
            fullDate = LocalDateTime.parse(this.date + " " + this.time, formatter).toEpochSecond(ZoneOffset.UTC).toString()
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                // Handle selected date (year, month, dayOfMonth)
                this.date="";
                val selectedDate = formatDate(year, month, dayOfMonth)
                binding.layoutLessonDate.tvLessonDate.text = selectedDate
                this.date = "${year}" + "-" + "${month}" + "-" + "${dayOfMonth}";
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH) // day related to chosen month
        )

        datePickerDialog.show()
        if(this.date != "" && this.time != ""){
            fullDate = LocalDateTime.parse(this.date + " " + this.time, formatter).toEpochSecond(ZoneOffset.UTC).toString()
        }
    }

    private fun formatDate(year: Int, month: Int, day: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        return "${day}/${month + 1}/${year}"
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StudentLobbyFragment()
    }
}
