package com.our.domain.features.phase_one.usecases

import com.our.domain.base.models.Result
import com.our.domain.base.usecases.BaseSuspendedUseCase
import com.our.domain.features.phase_one.models.remote.Oreder
import com.our.domain.features.phase_one.repositories.PhaseOneRepository
import javax.inject.Inject

class GetTeacherOrdersUseCase @Inject constructor(
    private val phaseOneRepository: PhaseOneRepository
) : BaseSuspendedUseCase<Int, Result<List<Oreder>>>() {

    override suspend fun invoke(param: Int): Result<List<Oreder>> =
        phaseOneRepository.getTeacherOrders(param)}