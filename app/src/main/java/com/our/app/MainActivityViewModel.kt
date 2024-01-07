package com.our.app

import com.our.app.base.BaseViewModelImpl
import com.our.data.base.datasources.Prefs
import com.our.domain.features.phase_one.usecases.PostFCMTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

abstract class MainActivityViewModel : BaseViewModelImpl() {
    abstract fun postFcmToken(token: String)
}

@HiltViewModel
class MainActivityViewModelImpl @Inject constructor(
    private val prefs: Prefs,
    private val postFCMTokenUseCase: PostFCMTokenUseCase
) : MainActivityViewModel() {

    override fun postFcmToken(token: String) {
        prefs.putString(Prefs.FCM_TOKEN, token)

        val id: Int? = if (prefs.contains(Prefs.STUDENT_ID)) {
            prefs.getInt(Prefs.STUDENT_ID)
        } else if (prefs.contains(Prefs.TEACHER_ID)) {
            prefs.getInt(Prefs.TEACHER_ID)
        } else null

        id?.let {
            launch {
                postFCMTokenUseCase.invoke(it)
            }
        }
    }
}