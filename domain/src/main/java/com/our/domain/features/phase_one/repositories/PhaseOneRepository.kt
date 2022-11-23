package com.our.domain.features.phase_one.repositories

import com.our.domain.base.models.Result
import com.our.domain.features.phase_one.models.remote.Lesson
import com.our.domain.features.phase_one.models.remote.Subject
import com.our.domain.features.phase_one.models.remote.SubjectBranch

interface PhaseOneRepository {
    suspend fun getSubjects(): Result<List<Subject>>
    suspend fun getSubjectBranches(subjectId: Int): Result<List<SubjectBranch>>
    suspend fun getLessons(subjectBranchId: Int): Result<List<Lesson>>
}