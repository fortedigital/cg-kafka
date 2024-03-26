package com.fortedigital.service.formats

import kotlinx.serialization.Serializable

@Serializable
class TeamRegistration (
    val messageId: String,
    val created: String,
    val answer: String,
    override val teamName: String,
    override val questionId: String,
    override val category: Category,
) : CommonQuestion
