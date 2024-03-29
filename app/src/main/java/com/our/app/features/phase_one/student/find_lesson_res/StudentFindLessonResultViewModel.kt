package com.our.app.features.phase_one.student.find_lesson_res

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.our.app.base.BaseViewModelImpl
import com.our.data.base.datasources.Prefs
import com.our.domain.features.phase_one.models.local.GotStudentResponseSealed
import com.our.domain.features.phase_one.usecases.GetLessonsUseCase
import com.our.domain.features.phase_one.usecases.PostOrderLessonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

abstract class StudentFindLessonResultViewModel : BaseViewModelImpl() {
    abstract val studentLobbyResponseLiveData: LiveData<GotStudentResponseSealed>
    abstract fun getLessons(levels: List<Int>, price: Int, timestamp: Long)
    abstract fun orderLesson(lesson: PostOrderLessonUseCase.OrderInfo)
}

@HiltViewModel
class StudentFindLessonResultViewModelImpl @Inject constructor(
    private val prefs: Prefs,
    private val getLessonsUseCase: GetLessonsUseCase,
    private val postOrderLessonUseCase: PostOrderLessonUseCase,
) : StudentFindLessonResultViewModel() {

    override val studentLobbyResponseLiveData = MutableLiveData<GotStudentResponseSealed>()

    override fun getLessons(levels: List<Int>, price: Int, timestamp: Long) {
        launch {
            var txt = ""
            levels.forEach {
                txt = "$txt$it,"
            }
            txt = txt.substring(0, txt.length - 1);
            studentLobbyResponseLiveData.postValue(
                getLessonsUseCase.invoke(
                    GetLessonsUseCase.GetLessonModel(
                        txt,
                        price.toDouble(),
                        timestamp
                    )
                )
            )
        }
    }

    override fun orderLesson(lesson: PostOrderLessonUseCase.OrderInfo) {
        launch {
            studentLobbyResponseLiveData.postValue(postOrderLessonUseCase.invoke(lesson))
        }
    }
}