package com.our.app.features.phase_one.teacherlobby

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.our.app.base.BaseViewModelImpl
import com.our.app.base.DisplayProgressTypes
import com.our.domain.features.phase_one.models.local.GotTeacherLobbyResponseSealed
import com.our.domain.features.phase_one.usecases.PostTeacherCreateInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

abstract class TeacherLobbyViewModel : BaseViewModelImpl() {
    abstract val teacherLobbyResponseLiveData: LiveData<GotTeacherLobbyResponseSealed>
    abstract fun postTeacherCreateInfo(createInfo: Map<String, String>)
}

@HiltViewModel
class TeacherLobbyViewModelImpl @Inject constructor(
    private val postTeacherCreateInfoUseCase: PostTeacherCreateInfoUseCase
) : TeacherLobbyViewModel() {
    override val teacherLobbyResponseLiveData = MutableLiveData<GotTeacherLobbyResponseSealed>()

    override fun postTeacherCreateInfo(createInfo: Map<String, String>) {
        launch(
            displayProgressType = DisplayProgressTypes.PROGRESS_BAR
        ) {
            teacherLobbyResponseLiveData.postValue(postTeacherCreateInfoUseCase.invoke(createInfo))
        }
    }
}