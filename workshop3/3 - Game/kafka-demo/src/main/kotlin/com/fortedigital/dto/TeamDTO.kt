package com.fortedigital.dto

import com.fortedigital.service.formats.Category
import kotlinx.serialization.Serializable

@Serializable
data class TeamDTO(
    val id: Int,
    val name: String,
    val score: Int,
    val hexColor: String,
    val answers: List<CategoryScoreDTO>
)


@Serializable
data class CategoryScoreDTO(
    val position: Int,
    val category: Category,
    val totalScore: Int,
    val totalAnswers: Int,
    val hasError: Boolean
)
