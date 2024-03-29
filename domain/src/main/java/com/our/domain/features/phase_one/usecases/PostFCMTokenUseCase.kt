package com.our.domain.features.phase_one.usecases

import com.our.domain.base.models.Result
import com.our.domain.base.usecases.BaseSuspendedUseCase
import com.our.domain.features.phase_one.models.local.GotFcmToken
import com.our.domain.features.phase_one.models.local.GotTeacherError
import com.our.domain.features.phase_one.models.local.GotTeacherLobbyResponseSealed
import com.our.domain.features.phase_one.repositories.PhaseOneRepository
import javax.inject.Inject

class PostFCMTokenUseCase @Inject constructor(
    private val phaseOneRepository: PhaseOneRepository
) : BaseSuspendedUseCase<Int, GotTeacherLobbyResponseSealed>() {

    override suspend fun invoke(param: Int): GotTeacherLobbyResponseSealed =
        when (val res = phaseOneRepository.postFcmToken(param)) {
            is Result.Success -> GotFcmToken
            is Result.Error -> GotTeacherError
        }
}