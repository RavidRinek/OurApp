package com.our.domain.features.phase_one.usecases

import com.our.domain.base.models.Result
import com.our.domain.base.usecases.BaseSuspendedUseCase
import com.our.domain.features.phase_one.models.local.GotError
import com.our.domain.features.phase_one.models.local.GotStudentLobbyResponseSealed
import com.our.domain.features.phase_one.models.local.GotSubjects
import com.our.domain.features.phase_one.models.remote.Subject
import com.our.domain.features.phase_one.repositories.PhaseOneRepository
import javax.inject.Inject

class GetSubjectsUseCase @Inject constructor(
    private val phaseOneRepository: PhaseOneRepository
) : BaseSuspendedUseCase<Unit, GotStudentLobbyResponseSealed>() {

    override suspend fun invoke(param: Unit): GotStudentLobbyResponseSealed =
        when (val res = phaseOneRepository.getSubjects()) {
            is Result.Success -> GotSubjects(res.data)
            is Result.Error -> GotError
        }
}