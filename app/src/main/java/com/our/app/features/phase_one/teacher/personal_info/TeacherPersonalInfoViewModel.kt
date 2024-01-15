package com.our.app.features.phase_one.teacher.personal_info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.our.app.base.BaseViewModelImpl
import com.our.app.base.DisplayProgressTypes
import com.our.data.base.datasources.Prefs
import com.our.domain.features.phase_one.models.local.GotTeacherPersonalInfo
import com.our.domain.features.phase_one.usecases.GetTeacherByIdUseCase
import com.our.domain.features.phase_one.usecases.PostFCMTokenUseCase
import com.our.domain.features.phase_one.usecases.PostTeacherCreateInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

abstract class TeacherPersonalInfoViewModel : BaseViewModelImpl() {
    abstract val teacherPersonalInfoResponseLiveData: LiveData<GotTeacherPersonalInfo>
    abstract fun postTeacherCreateInfo(createInfo: Map<String, String>)
    abstract fun getTeacherById()
}


@HiltViewModel
class TeacherPersonalInfoViewModelImpl @Inject constructor(
    private val prefs: Prefs,
    private val postTeacherCreateInfoUseCase: PostTeacherCreateInfoUseCase,
    private val postFCMTokenUseCase: PostFCMTokenUseCase,
    private val getTeacherByIdUseCase: GetTeacherByIdUseCase
) : TeacherPersonalInfoViewModel() {
    override val teacherPersonalInfoResponseLiveData = MutableLiveData<GotTeacherPersonalInfo>()

    init {
        getTeacherById()
    }

    override fun getTeacherById() {
        launch(
            displayProgressType = DisplayProgressTypes.PROGRESS_BAR
        ) {
            prefs.getInt(Prefs.TEACHER_ID).takeIf { it > 0 }?.let {
                val res = getTeacherByIdUseCase.invoke(it)
                if (res is GotTeacherPersonalInfo) {
                    teacherPersonalInfoResponseLiveData.postValue(res)
                }
            }
        }
    }

    override fun postTeacherCreateInfo(createInfo: Map<String, String>) {
        launch(
            displayProgressType = DisplayProgressTypes.PROGRESS_BAR
        ) {
            val res = postTeacherCreateInfoUseCase.invoke(createInfo)
            if (res is GotTeacherPersonalInfo) {
                prefs.putBoolean(Prefs.COMPLETED_TEACHER_PERSONAL_INFO_REGISTRATION, true)
                prefs.putInt(Prefs.TEACHER_ID, res.teacherProfile.teacherId)
                postFCMTokenUseCase.invoke(res.teacherProfile.teacherId)
                teacherPersonalInfoResponseLiveData.postValue(res)
            }
        }
    }
}