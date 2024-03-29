package com.our.domain.features.phase_one.repositories

import com.our.domain.base.models.Result
import com.our.domain.features.phase_one.models.remote.Student
import com.our.domain.features.phase_one.models.remote.StudentLessonOffers
import com.our.domain.features.phase_one.models.remote.Subject
import com.our.domain.features.phase_one.models.remote.SubjectBranch
import com.our.domain.features.phase_one.models.remote.Oreder
import com.our.domain.features.phase_one.models.remote.TeacherProfile
import com.our.domain.features.phase_one.usecases.PostOrderLessonUseCase
import com.our.domain.features.phase_one.usecases.PostStudentCreateUseCase
import com.our.domain.features.phase_one.usecases.PostTeacherInfoUseCase
import java.sql.Timestamp

interface PhaseOneRepository {
    suspend fun getSubjects(): Result<List<Subject>>
    suspend fun getSubjectBranches(subjectId: Int): Result<List<SubjectBranch>>
    suspend fun getLessons(levels: String, price: Double, timestamp: Long): Result<List<StudentLessonOffers>>
    suspend fun getTeacherById(teacherId: Int): Result<TeacherProfile>
    suspend fun postTeacherCreateInfo(teacherInfo: Map<String, String>): Result<TeacherProfile>
    suspend fun postTeacherInfo(updateTeacherInfo: PostTeacherInfoUseCase.UpdateTeacherInfo): Result<Unit>
    suspend fun postFcmToken(id: Int): Result<Unit>
    suspend fun getTeacherOrders(teacherId: Int): Result<List<Oreder>>
    suspend fun postStudentCreate(createStudent: PostStudentCreateUseCase.CreateStudent): Result<Student>
    suspend fun postOrderLesson(orderInfo: PostOrderLessonUseCase.OrderInfo): Result<Unit>
    suspend fun getStudentById(studentId: Int): Result<Student>
    suspend fun getStudentOrders(studentId: Int): Result<List<Oreder>>
}