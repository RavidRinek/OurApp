package com.our.domain.features.phase_one.models.remote

data class Subject(
    override val id: Int,
    override val name: String,
    val imgUrl: String
) : BaseSubject(id, name)