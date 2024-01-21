package com.our.data.features.phase_one.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.our.domain.features.phase_one.models.remote.TeacherOrder

@Keep
data class UpcomingLessons(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("student")
    val student: StudentResponse? = null,
    @SerializedName("lesson")
    val lessonResponse: LessonResponse? = null,
    @SerializedName("teacher")
    val teacherResponse: TeacherProfileResponse? = null,
    @SerializedName("subject")
    val subjectResponse: SubjectResponse? = null,
    @SerializedName("urlVideo")
    val videoUrlResponse: String? = null
)

fun UpcomingLessons.toDomain(): TeacherOrder =
    TeacherOrder(
        id = id ?: 0,
        student = (student ?: StudentResponse()).toDomain(),
        lesson = (lessonResponse ?: LessonResponse()).toDomain(),
        teacher = (teacherResponse ?: TeacherProfileResponse()).toDomain(),
        subject = (subjectResponse ?: SubjectResponse()).toDomain(),
        videoUrl = videoUrlResponse ?: ""
    )