package com.our.domain.features.phase_one.usecases

import com.our.domain.base.models.Result
import com.our.domain.base.usecases.BaseSuspendedUseCase
import com.our.domain.features.phase_one.models.local.GotError
import com.our.domain.features.phase_one.models.local.GotLessons
import com.our.domain.features.phase_one.models.local.GotStudentLobbyResponseSealed
import com.our.domain.features.phase_one.repositories.PhaseOneRepository
import javax.inject.Inject

class GetLessonsUseCase @Inject constructor(
    private val phaseOneRepository: PhaseOneRepository
) : BaseSuspendedUseCase<Int, GotStudentLobbyResponseSealed>() {

    override suspend fun invoke(param: Int): GotStudentLobbyResponseSealed =
        when (val res = phaseOneRepository.getLessons(param)) {
            is Result.Success -> {
                if (res.data.isEmpty()) {
                    GotError
                } else {
                    GotLessons(res.data)
                }
            }
            is Result.Error -> GotError
        }
}