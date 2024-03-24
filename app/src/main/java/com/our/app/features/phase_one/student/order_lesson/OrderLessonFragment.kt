package com.our.app.features.phase_one.student.order_lesson

import android.os.Bundle
import android.os.Parcelable
import android.text.format.DateFormat
import android.text.format.DateUtils
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.our.app.R
import com.our.app.base.BaseFragment
import com.our.app.databinding.FragmentOrderLessonBinding
import com.our.app.utilities.bindingDelegates.viewBinding
import com.our.data.base.datasources.Prefs
import com.our.domain.features.phase_one.models.local.GotCreatedStudent
import com.our.domain.features.phase_one.models.local.GotOrderedLesson
import com.our.domain.features.phase_one.models.local.GotStudentError
import com.our.domain.features.phase_one.usecases.PostOrderLessonUseCase
import com.our.domain.features.phase_one.usecases.PostStudentCreateUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject


@AndroidEntryPoint
class OrderLessonFragment :
    BaseFragment<OrderLessonViewModel>(R.layout.fragment_order_lesson) {

    override val viewModel: OrderLessonViewModel by viewModels<OrderLessonViewModelImpl>()
    private val binding by viewBinding(FragmentOrderLessonBinding::bind)
    private var lessonId: Int = 0
    private var timestamp: Long = 0

    @Inject
    lateinit var prefs: Prefs


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (prefs.getInt(Prefs.STUDENT_ID) > 0) {
            binding.tvStudentHeader.visibility = View.GONE
            binding.cvStudentContainer.visibility = View.GONE
            binding.agreement.visibility = View.GONE
        }

        (arguments?.getParcelable(K_LESSON_INFO) as? OrderLessonUi)?.let {
            lessonId = it.lessonId
            timestamp = it.time
            binding.apply {
                levelOfClass.text = "תיכונית"
                lessonPrice.text = "100"//it.price.toString()
                teachersName.text = it.name
                lessonDate.text = "${getDate(it.time)} ${getTime(it.time)}"

                etName.addTextChangedListener {
                    updateReserveButtonState()
                }
                etPhone.addTextChangedListener {
                    updateReserveButtonState()
                }
                etMail.addTextChangedListener {
                    updateReserveButtonState()
                }
                agreement.setOnCheckedChangeListener { compoundButton, b ->
                    updateReserveButtonState()
                }

                reserveButton
                reserveButton.setOnClickListener {
                    if (prefs.getInt(Prefs.STUDENT_ID) > 0) {
                        orderLesson()
                    } else {
                        viewModel.createStudent(
                            PostStudentCreateUseCase.CreateStudent(
                                studentName = etName.text.toString(),
                                studentLastName = etMail.text.toString(),
                                studentPhone = etPhone.text.toString(),
                                studentMail = etMail.text.toString()
                            )
                        )
                    }
                }
            }
        }
    }

    private fun updateReserveButtonState() {
        binding.reserveButton.isEnabled = !(binding.etName.text.isEmpty()
                || binding.etMail.text.isEmpty()
                || binding.etPhone.text.isEmpty()
                || !binding.agreement.isChecked)
    }

    private fun getDate(time: Long): String {
        val cal: Calendar = Calendar.getInstance(Locale.ENGLISH)
        cal.setTimeInMillis(time * 1000)
        return DateFormat.format("dd-MM-yyyy", cal).toString()
    }

    private fun getTime(timestamp: Long): String {
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = timestamp * 1000L
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        var hourInString = hour.toString()
        if (hour < 10) {
            hourInString = "0$hour"
        }
        val minutes = calendar.get(Calendar.MINUTE)
        var minInString = minutes.toString()
        if (minutes < 10) {
            minInString = "0$minutes"
        }
//            val date = DateFormat.format("dd-MM-yyyy", calendar).toString()
        return "$hourInString:$minInString"
    }

    override fun observeData() {
        super.observeData()
        viewModel.orderLessonLiveData.observe {
            when (it) {
                GotStudentError -> {
                    println("${OrderLessonFragment::class.java.simpleName} GotStudentError")
                }

                is GotCreatedStudent -> {
                    println("${OrderLessonFragment::class.java.simpleName} GotCreatedStudent")
                    if (it.student.id > 0 && !prefs.contains(Prefs.STUDENT_ID)) {
                        prefs.putInt(Prefs.STUDENT_ID, it.student.id)
                    }
                    orderLesson()
                }

                GotOrderedLesson -> {
                    prefs.putBoolean(Prefs.COMPLETED_STUDENT_FULL_REGISTRATION, true)
                    findNavController().navigate(R.id.action_orderLessonFragment_to_studentLobbyFragment)
                }

                else -> {
                    println("${OrderLessonFragment::class.java.simpleName} ELSE")
                }
            }
        }
    }

    private fun orderLesson() {
        arguments?.apply {
            viewModel.orderLesson(
                PostOrderLessonUseCase.OrderInfo(
                    studentId = prefs.getInt(Prefs.STUDENT_ID),
                    lessonId = lessonId,
                    lessonTimestamp = timestamp
                )
            )
        }
    }

    companion object {
        const val K_LESSON_INFO = "lesson_info"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OrderLessonFragment()
    }
}

@Parcelize
data class OrderLessonUi(
    val lessonId: Int,
    val price: Double,
    val name: String,
    val time: Long,
) : Parcelable
