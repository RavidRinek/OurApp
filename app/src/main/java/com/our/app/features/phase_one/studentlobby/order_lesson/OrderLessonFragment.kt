package com.our.app.features.phase_one.studentlobby.order_lesson

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.fragment.app.viewModels
import com.our.app.R
import com.our.app.base.BaseFragment
import com.our.app.databinding.FragmentOrderLessonBinding
import com.our.app.utilities.bindingDelegates.viewBinding
import com.our.domain.features.phase_one.models.local.GotCreatedStudent
import com.our.domain.features.phase_one.models.local.GotOrderedLesson
import com.our.domain.features.phase_one.models.local.GotStudentError
import com.our.domain.features.phase_one.usecases.PostOrderLessonUseCase
import com.our.domain.features.phase_one.usecases.PostStudentCreateUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize


@AndroidEntryPoint
class OrderLessonFragment :
    BaseFragment<OrderLessonViewModel>(R.layout.fragment_order_lesson) {

    override val viewModel: OrderLessonViewModel by viewModels<OrderLessonViewModelImpl>()
    private val binding by viewBinding(FragmentOrderLessonBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (arguments?.getParcelable(K_LESSON_INFO) as? OrderLessonUi)?.let {
            binding.apply {
                levelOfClass.text = it.level
                lessonPrice.text = it.price
                teachersName.text = it.name
                lessonDate.text = it.time

                reserveButton.setOnClickListener {
                    viewModel.createStudent(
                        PostStudentCreateUseCase.CreateStudent(
                            studentName = etName.text.toString(),
                            studentLastName = etMail.text.toString(),
                            studentPhone = etPhone.text.toString()
                        )
                    )
                }
            }
        }
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
                    arguments?.apply {
                    viewModel.orderLesson(
                        PostOrderLessonUseCase.OrderInfo(
                            studentId = it.student.id,
                            lessonId = arguments?.getInt("lessonId") ?: 0
                        )
                    )
                    }
                }

                GotOrderedLesson -> {
                    println("${OrderLessonFragment::class.java.simpleName} GotOrderedLesson")
                }

                else -> {
                    println("${OrderLessonFragment::class.java.simpleName} ELSE")
                }
            }
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
    val level: String,
    val price: String,
    val name: String,
    val time: String,
) : Parcelable
