package com.our.app.features.phase_one.studentlobby

import com.our.domain.features.phase_one.models.remote.BaseSubject

interface SubjectViewHolderInter {
    fun bind(baseSubject: BaseSubject)
}