package com.our.domain.features.phase_one.usecases

import com.our.domain.base.models.Result
import com.our.domain.base.usecases.BaseSuspendedUseCase
import com.our.domain.features.phase_one.models.local.GotStudentError
import com.our.domain.features.phase_one.models.local.GotLessons
import com.our.domain.features.phase_one.models.local.GotStudentResponseSealed
import com.our.domain.features.phase_one.repositories.PhaseOneRepository
import javax.inject.Inject

class GetLessonsUseCase @Inject constructor(
    private val phaseOneRepository: PhaseOneRepository
) : BaseSuspendedUseCase<GetLessonsUseCase.GetLessonModel, GotStudentResponseSealed>() {

    override suspend fun invoke(getLessonModel: GetLessonModel): GotStudentResponseSealed =
        when (val res =
            phaseOneRepository.getLessons(
                getLessonModel.levels,
                getLessonModel.price,
                getLessonModel.timestamp
            )) {
            is Result.Success -> {
                if (res.data.isEmpty()) {
                    GotStudentError
                } else {
                    GotLessons(res.data)
                }
            }

            is Result.Error -> GotStudentError
        }

    data class GetLessonModel(val levels: String, val price: Double, val timestamp: Long)
}