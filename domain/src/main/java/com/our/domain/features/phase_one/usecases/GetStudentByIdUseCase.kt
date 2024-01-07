package com.our.domain.features.phase_one.usecases

import com.our.domain.base.models.Result
import com.our.domain.base.usecases.BaseSuspendedUseCase
import com.our.domain.features.phase_one.models.local.GotStudentError
import com.our.domain.features.phase_one.models.local.GotStudentResponseSealed
import com.our.domain.features.phase_one.models.local.GotStudentPersonalInfo
import com.our.domain.features.phase_one.repositories.PhaseOneRepository
import javax.inject.Inject

class GetStudentByIdUseCase @Inject constructor(
    private val phaseOneRepository: PhaseOneRepository
) : BaseSuspendedUseCase<Int, GotStudentResponseSealed>() {

    override suspend fun invoke(param: Int): GotStudentResponseSealed =
        when (val res = phaseOneRepository.getStudentById(param)) {
            is Result.Success -> GotStudentPersonalInfo(res.data)
            is Result.Error -> GotStudentError
        }
}