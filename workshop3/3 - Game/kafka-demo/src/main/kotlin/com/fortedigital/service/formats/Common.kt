package com.fortedigital.service.formats

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
open class Common(val type: Type, val messageId: String, val category: Category, val created: String)

interface CommonQuestion {
    val questionId: String
    val category: Category
    val teamName: String
}
@Serializable
enum class Type {
    QUESTION, ANSWER
}

@Serializable
enum class Category {
    @SerialName("team-registration")
    TEAM_REGISTRATION,
    @SerialName("ping-pong")
    PING_PONG
}
