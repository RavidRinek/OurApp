package com.our.data.features.phase_one.repositories

import com.our.data.features.phase_one.datasources.PhaseOneDataSource
import com.our.data.features.phase_one.models.*
import com.our.domain.base.models.Result
import com.our.domain.base.models.map
import com.our.domain.features.phase_one.models.remote.Student
import com.our.domain.features.phase_one.models.remote.StudentLessonOffers
import com.our.domain.features.phase_one.models.remote.Subject
import com.our.domain.features.phase_one.models.remote.SubjectBranch
import com.our.domain.features.phase_one.models.remote.TeacherOrder
import com.our.domain.features.phase_one.models.remote.TeacherProfile
import com.our.domain.features.phase_one.repositories.PhaseOneRepository
import com.our.domain.features.phase_one.usecases.PostOrderLessonUseCase
import com.our.domain.features.phase_one.usecases.PostStudentCreateUseCase
import com.our.domain.features.phase_one.usecases.PostTeacherInfoUseCase
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

    override suspend fun getLessons(levels: String): Result<List<StudentLessonOffers>> =
        phaseOneDataSource.getLessons(levels).map {
            (it as LessonsResponse).toDomain()
        }

    override suspend fun getTeacherById(teacherId: Int): Result<TeacherProfile> =
        phaseOneDataSource.getTeacherById(teacherId).map {
            (it as GetTeacherResponse).toDomain()
        }

    override suspend fun postTeacherCreateInfo(teacherInfo: Map<String, String>): Result<TeacherProfile> =
        phaseOneDataSource.postTeacherCreateInfo(teacherInfo).map {
            (it as GetTeacherResponse).toDomain()
        }

    override suspend fun postTeacherInfo(updateTeacherInfo: PostTeacherInfoUseCase.UpdateTeacherInfo): Result<Unit> =
        phaseOneDataSource.postTeacherInfo(updateTeacherInfo).map {
            Unit
        }

    override suspend fun postFcmToken(id: Int): Result<Unit> =
        phaseOneDataSource.postCreateFcmToken(id).map {
            Unit
        }

    override suspend fun getTeacherOrders(teacherId: Int): Result<List<TeacherOrder>> =
        phaseOneDataSource.getTeacherOrders(teacherId).map {
            (it as GetTeacherOrdersResponse).toDomain()
        }

    override suspend fun postStudentCreate(createStudent: PostStudentCreateUseCase.CreateStudent): Result<Student> =
        phaseOneDataSource.postStudentCreate(createStudent).map {
            (it as GetStudentResponse).toDomain()
        }

    override suspend fun postOrderLesson(orderInfo: PostOrderLessonUseCase.OrderInfo): Result<Unit> =
        phaseOneDataSource.postOrderLesson(orderInfo).map {
            Unit
        }

    override suspend fun getStudentById(studentId: Int): Result<Student> =
        phaseOneDataSource.getStudentById(studentId).map {
            (it as GetStudentResponse).toDomain()
        }
}