package com.our.app.features.phase_one.student.find_lesson

import com.our.domain.features.phase_one.models.remote.BaseSubject

interface SubjectViewHolderInter {
    fun bind(baseSubject: BaseSubject)
}