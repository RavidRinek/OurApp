package com.our.domain.features.phase_one.usecases

import com.our.domain.base.models.Result
import com.our.domain.base.usecases.BaseSuspendedUseCase
import com.our.domain.features.phase_one.models.local.*
import com.our.domain.features.phase_one.repositories.PhaseOneRepository
import javax.inject.Inject

class GetSubjectsLevelsKnowledgeUseCase @Inject constructor(
    private val phaseOneRepository: PhaseOneRepository
) : BaseSuspendedUseCase<Unit, GotTeacherLobbyResponseSealed>() {

    override suspend fun invoke(param: Unit): GotTeacherLobbyResponseSealed =
        when (val res = phaseOneRepository.getSubjects()) {
            is Result.Success -> GotSubjectLevelsForTeacherKnowledgeInfo(res.data)
            is Result.Error -> GotTeacherError
        }
}