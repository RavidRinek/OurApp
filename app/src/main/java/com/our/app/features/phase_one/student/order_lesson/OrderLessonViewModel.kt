package com.our.app.features.phase_one.student.order_lesson

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.our.app.base.BaseViewModelImpl
import com.our.domain.features.phase_one.models.local.GotStudentLobbyResponseSealed
import com.our.domain.features.phase_one.usecases.PostOrderLessonUseCase
import com.our.domain.features.phase_one.usecases.PostStudentCreateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

abstract class OrderLessonViewModel : BaseViewModelImpl() {
    abstract val orderLessonLiveData: LiveData<GotStudentLobbyResponseSealed>
    abstract fun createStudent(createStudent: PostStudentCreateUseCase.CreateStudent)
    abstract fun orderLesson(lesson: PostOrderLessonUseCase.OrderInfo)
}

@HiltViewModel
class OrderLessonViewModelImpl @Inject constructor(
    private val postStudentCreateUseCase: PostStudentCreateUseCase,
    private val postOrderLessonUseCase: PostOrderLessonUseCase
) : OrderLessonViewModel() {

    override val orderLessonLiveData = MutableLiveData<GotStudentLobbyResponseSealed>()

    override fun createStudent(createStudent: PostStudentCreateUseCase.CreateStudent) {
        launch {
            orderLessonLiveData.postValue(postStudentCreateUseCase.invoke(createStudent))
        }
    }

    override fun orderLesson(lesson: PostOrderLessonUseCase.OrderInfo) {
        launch {
            orderLessonLiveData.postValue(postOrderLessonUseCase.invoke(lesson))
        }
    }
}