package com.our.domain.features.phase_one.models.remote

data class Subject(
    override val id: Int,
    override val name: String,
    val imgUrl: String,
    val subjectLevel: List<SubjectLevel>
) : BaseSubject(id, name), java.io.Serializable