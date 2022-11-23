package com.our.domain.features.phase_one.models.remote

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Lesson(
    val id: Int,
    val price: Double,
    val durationInMin: Int,
    val time: String,
    val ratingInPercentage: Int,
    val subjectBranchId: Int,
    val subjectBranchName: String,
    val teacherId: Int,
    val teacherName: String
) : Parcelable
