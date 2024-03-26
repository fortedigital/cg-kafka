package com.fortedigital.service.formats

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
open class Common(val type: Type, val category: Category)

interface CommonMessage {
    val messageId: String
    val questionId: String
    val category: Category
    val teamName: String
}

@Serializable
class QuestionMessage (
    val created: String,
    val question: String,
    val messageId: String,
    val category: Category
)

@Serializable
class AnswerMessage (
    val created: String,
    val answer: String,
    override val messageId: String,
    override val teamName: String,
    override val questionId: String,
    override val category: Category,
) : CommonMessage


@Serializable
enum class Type {
    QUESTION, ANSWER
}

@Serializable
enum class Category(val score: Int) {
    @SerialName("team-registration")
    TEAM_REGISTRATION(10),
    @SerialName("ping-pong")
    PING_PONG(20),
    @SerialName("base64")
    BASE64(30)
}
