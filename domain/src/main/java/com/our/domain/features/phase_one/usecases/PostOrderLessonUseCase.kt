package com.our.domain.features.phase_one.usecases

import com.our.domain.base.models.Result
import com.our.domain.base.usecases.BaseSuspendedUseCase
import com.our.domain.features.phase_one.models.local.GotOrderedLesson
import com.our.domain.features.phase_one.models.local.GotStudentError
import com.our.domain.features.phase_one.models.local.GotStudentResponseSealed
import com.our.domain.features.phase_one.repositories.PhaseOneRepository
import javax.inject.Inject

class PostOrderLessonUseCase @Inject constructor(
    private val phaseOneRepository: PhaseOneRepository
) : BaseSuspendedUseCase<PostOrderLessonUseCase.OrderInfo, GotStudentResponseSealed>() {

    override suspend fun invoke(param: OrderInfo): GotStudentResponseSealed =
        when (val res = phaseOneRepository.postOrderLesson(param)) {
            is Result.Success -> GotOrderedLesson
            is Result.Error -> GotStudentError
        }

    data class OrderInfo(
        val studentId: Int,
        val lessonId: Int
    )
}