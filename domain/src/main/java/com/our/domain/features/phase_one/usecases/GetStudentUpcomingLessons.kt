package com.our.domain.features.phase_one.usecases

import com.our.domain.base.models.Result
import com.our.domain.base.usecases.BaseSuspendedUseCase
import com.our.domain.features.phase_one.models.local.GotStudentError
import com.our.domain.features.phase_one.models.local.GotStudentResponseSealed
import com.our.domain.features.phase_one.models.local.GotStudentUpcomingLessons
import com.our.domain.features.phase_one.models.remote.TeacherOrder
import com.our.domain.features.phase_one.repositories.PhaseOneRepository
import javax.inject.Inject

class GetStudentUpcomingLessons @Inject constructor(
    private val phaseOneRepository: PhaseOneRepository
) : BaseSuspendedUseCase<Int, GotStudentResponseSealed>() {

    override suspend fun invoke(param: Int): GotStudentResponseSealed {

        return when (val res = phaseOneRepository.getStudentOrders(param)) {
            is Result.Error -> GotStudentError
            is Result.Success -> GotStudentUpcomingLessons(res.data)
        }
    }
}