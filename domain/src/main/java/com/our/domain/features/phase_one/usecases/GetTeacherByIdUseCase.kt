package com.our.domain.features.phase_one.usecases

import com.our.domain.base.models.Result
import com.our.domain.base.usecases.BaseSuspendedUseCase
import com.our.domain.features.phase_one.models.local.*
import com.our.domain.features.phase_one.repositories.PhaseOneRepository
import javax.inject.Inject

class GetTeacherByIdUseCase @Inject constructor(
    private val phaseOneRepository: PhaseOneRepository
) : BaseSuspendedUseCase<Int, GotTeacherLobbyResponseSealed>() {

    override suspend fun invoke(param: Int): GotTeacherLobbyResponseSealed =
        when (val res = phaseOneRepository.getTeacherById(param)) {
            is Result.Success -> GotTeacherInfo(res.data)
            is Result.Error -> GotTeacherError
        }
}