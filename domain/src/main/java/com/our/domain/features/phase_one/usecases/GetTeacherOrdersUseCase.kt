package com.our.domain.features.phase_one.usecases

import com.our.domain.base.models.Result
import com.our.domain.base.usecases.BaseSuspendedUseCase
import com.our.domain.features.phase_one.models.local.GotFirstPageInfo
import com.our.domain.features.phase_one.models.local.GotTeacherError
import com.our.domain.features.phase_one.models.local.GotTeacherLobbyResponseSealed
import com.our.domain.features.phase_one.models.local.GotTeacherOrders
import com.our.domain.features.phase_one.repositories.PhaseOneRepository
import javax.inject.Inject

class GetTeacherOrdersUseCase @Inject constructor(
    private val phaseOneRepository: PhaseOneRepository
) : BaseSuspendedUseCase<Int, GotTeacherLobbyResponseSealed>() {

    override suspend fun invoke(param: Int): GotTeacherLobbyResponseSealed =
        when (val res = phaseOneRepository.getTeacherOrders(param)) {
            is Result.Success -> GotTeacherOrders
            is Result.Error -> GotTeacherError
        }
}