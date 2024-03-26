package com.fortedigital.service.formats

import kotlinx.datetime.Instant
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
    TEAM_REGISTRATION(5), // 1 TASK
    @SerialName("ping-pong")
    PING_PONG(10), // 10 TASKS
    @SerialName("arithmetic")
    ARITHMETIC(20), // 20 TASKS
    @SerialName("base64")
    BASE_64(30), // 5 TASKS
    @SerialName("is-a-prime")
    PRIME_NUMBER(50), // 10 TASKS
    @SerialName("transactions")
    TRANSACTIONS(100), // 20 TASKS
    @SerialName("min-max")
    MIN_MAX(200), // 5 TASKS
    @SerialName("deduplication")
    DEDUPLICATION(300), // 3 TASKS
}
