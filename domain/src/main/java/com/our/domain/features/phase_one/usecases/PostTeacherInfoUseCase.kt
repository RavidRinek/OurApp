package com.our.domain.features.phase_one.usecases

import android.os.Parcelable
import com.our.domain.base.models.Result
import com.our.domain.base.usecases.BaseSuspendedUseCase
import com.our.domain.features.phase_one.models.local.GotTeacherError
import com.our.domain.features.phase_one.models.local.GotTeacherLobbyResponseSealed
import com.our.domain.features.phase_one.models.local.FirstTeacherDetails
import com.our.domain.features.phase_one.repositories.PhaseOneRepository
import kotlinx.android.parcel.Parcelize
import javax.inject.Inject

class PostTeacherInfoUseCase @Inject constructor(
    private val phaseOneRepository: PhaseOneRepository
) : BaseSuspendedUseCase<PostTeacherInfoUseCase.UpdateTeacherInfo, GotTeacherLobbyResponseSealed>() {

    override suspend fun invoke(param: UpdateTeacherInfo): GotTeacherLobbyResponseSealed =
        when (phaseOneRepository.postTeacherInfo(param)) {
            is Result.Success -> FirstTeacherDetails
            is Result.Error -> GotTeacherError
        }

    @Parcelize
    data class UpdateTeacherInfo(
        val teacherId: Int,
        val teacherSubjectsLevelsId: List<Int>,
        val lessonInfo: LessonInfo,
        val degreeInfo: DegreeInfo
    ): Parcelable

    @Parcelize
    data class LessonInfo(
        val pricePer60m: Int,
        val pricePer40m: Int,
        val firstLessonFree: Boolean,
        val additionalInfo: String
    ): Parcelable

    @Parcelize
    data class DegreeInfo(val schoolName: String, val degreeName: String): Parcelable
}