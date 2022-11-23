package com.our.domain.features.phase_one.models.remote

data class SubjectBranch(
    override val id: Int,
    override val name: String,
    val description: String
) : BaseSubject(id, name)