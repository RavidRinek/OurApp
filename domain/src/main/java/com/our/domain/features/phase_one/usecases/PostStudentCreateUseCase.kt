package com.our.domain.features.phase_one.usecases

import com.our.domain.base.models.Result
import com.our.domain.base.usecases.BaseSuspendedUseCase
import com.our.domain.features.phase_one.models.local.GotCreatedStudent
import com.our.domain.features.phase_one.models.local.GotStudentError
import com.our.domain.features.phase_one.models.local.GotStudentResponseSealed
import com.our.domain.features.phase_one.repositories.PhaseOneRepository
import javax.inject.Inject

class PostStudentCreateUseCase @Inject constructor(
    private val phaseOneRepository: PhaseOneRepository
) : BaseSuspendedUseCase<PostStudentCreateUseCase.CreateStudent, GotStudentResponseSealed>() {

    override suspend fun invoke(param: CreateStudent): GotStudentResponseSealed =
        when (val res = phaseOneRepository.postStudentCreate(param)) {
            is Result.Success -> GotCreatedStudent(res.data)
            is Result.Error -> GotStudentError
        }

    data class CreateStudent(
        val studentName: String,
        val studentLastName: String,
        val studentPhone: String,
        val studentMail: String
    )
}