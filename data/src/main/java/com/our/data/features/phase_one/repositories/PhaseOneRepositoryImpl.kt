package com.our.data.features.phase_one.repositories

import com.our.data.features.phase_one.datasources.PhaseOneDataSource
import com.our.data.features.phase_one.models.*
import com.our.domain.base.models.Result
import com.our.domain.base.models.map
import com.our.domain.features.phase_one.models.remote.Lesson
import com.our.domain.features.phase_one.models.remote.Subject
import com.our.domain.features.phase_one.models.remote.SubjectBranch
import com.our.domain.features.phase_one.repositories.PhaseOneRepository
import javax.inject.Inject

class PhaseOneRepositoryImpl @Inject constructor(
    private val phaseOneDataSource: PhaseOneDataSource
) : PhaseOneRepository {

    override suspend fun getSubjects(): Result<List<Subject>> =
        phaseOneDataSource.getSubjects().map {
            (it as SubjectsResponse).toDomain()
        }

    override suspend fun getSubjectBranches(subjectId: Int): Result<List<SubjectBranch>> =
        phaseOneDataSource.getSubjectBranches(subjectId).map {
            (it as SubjectBranchesResponse).toDomain()
        }

    override suspend fun getLessons(subjectBranchId: Int): Result<List<Lesson>> =
        phaseOneDataSource.getLessons(subjectBranchId).map {
            (it as LessonsResponse).toDomain()
        }
}