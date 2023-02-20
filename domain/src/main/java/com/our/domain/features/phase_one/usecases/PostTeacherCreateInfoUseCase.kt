package com.our.domain.features.phase_one.usecases

import com.our.domain.base.models.Result
import com.our.domain.base.usecases.BaseSuspendedUseCase
import com.our.domain.features.phase_one.models.local.GotTeacherInfo
import com.our.domain.features.phase_one.models.local.GotTeacherError
import com.our.domain.features.phase_one.models.local.GotTeacherLobbyResponseSealed
import com.our.domain.features.phase_one.repositories.PhaseOneRepository
import javax.inject.Inject

class PostTeacherCreateInfoUseCase @Inject constructor(
    private val phaseOneRepository: PhaseOneRepository
) : BaseSuspendedUseCase<Map<String, String>, GotTeacherLobbyResponseSealed>() {

    override suspend fun invoke(param: Map<String, String>): GotTeacherLobbyResponseSealed =
        when (val res = phaseOneRepository.postTeacherCreateInfo(param)) {
            is Result.Success -> GotTeacherInfo(res.data)
            is Result.Error -> GotTeacherError
        }
}