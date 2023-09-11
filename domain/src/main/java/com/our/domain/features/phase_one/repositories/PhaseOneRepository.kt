package com.our.domain.features.phase_one.repositories

import com.our.domain.base.models.Result
import com.our.domain.features.phase_one.models.remote.Lesson
import com.our.domain.features.phase_one.models.remote.Student
import com.our.domain.features.phase_one.models.remote.StudentLessonOffers
import com.our.domain.features.phase_one.models.remote.Subject
import com.our.domain.features.phase_one.models.remote.SubjectBranch
import com.our.domain.features.phase_one.models.remote.TeacherOrder
import com.our.domain.features.phase_one.models.remote.TeacherProfile
import com.our.domain.features.phase_one.usecases.PostOrderLessonUseCase
import com.our.domain.features.phase_one.usecases.PostStudentCreateUseCase
import com.our.domain.features.phase_one.usecases.PostTeacherInfoUseCase

interface PhaseOneRepository {
    suspend fun getSubjects(): Result<List<Subject>>
    suspend fun getSubjectBranches(subjectId: Int): Result<List<SubjectBranch>>
    suspend fun getLessons(levels: String): Result<List<StudentLessonOffers>>
    suspend fun getTeacherById(teacherId: Int): Result<TeacherProfile>
    suspend fun postTeacherCreateInfo(teacherInfo: Map<String, String>): Result<TeacherProfile>
    suspend fun postTeacherInfo(updateTeacherInfo: PostTeacherInfoUseCase.UpdateTeacherInfo): Result<Unit>
    suspend fun postFcmToken(): Result<Unit>
    suspend fun getTeacherOrders(teacherId: Int): Result<List<TeacherOrder>>
    suspend fun postStudentCreate(createStudent: PostStudentCreateUseCase.CreateStudent): Result<Student>
    suspend fun postOrderLesson(orderInfo: PostOrderLessonUseCase.OrderInfo): Result<Unit>
}