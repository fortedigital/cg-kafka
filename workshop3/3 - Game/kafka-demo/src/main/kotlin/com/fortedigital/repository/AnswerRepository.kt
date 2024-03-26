package com.fortedigital.repository

import com.fortedigital.dto.AnswerDTO
import org.jetbrains.exposed.dao.id.IntIdTable
import com.fortedigital.repository.DatabaseFactory.Companion.dbQuery
import com.fortedigital.service.formats.Category
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq


class Answer (
    val id: Int,
    val teamId: Int,
    val score: Int,
    val messageId: String,
    val questionId: String,
    val category: Category,
    val created: String,
)  {
    fun toDTO() = AnswerDTO(category, score, questionId)
}
class AnswerRepository {
     object AnswerTable : IntIdTable() {
        val teamId = reference("team_id", TeamRepository.TeamTable.id)
        val score = integer("score")
        val messageId = varchar("message_id", 64)
        val questionId = varchar("question_id", 64)
        val category = enumeration<Category>("category")
        val created = varchar("created", 64)

        fun toModel(it: ResultRow) = Answer(
            it[id].value,
            it[teamId].value,
            it[score],
            it[messageId],
            it[questionId],
            it[category],
            it[created],
        )
    }

    suspend fun create(answer: Answer): Int = dbQuery {
        AnswerTable.insertAndGetId {
            it[teamId] = answer.teamId
            it[score] = answer.score
            it[messageId] = answer.messageId
            it[questionId] = answer.questionId
            it[category] = answer.category
            it[created] = answer.created
        }.value
    }


    suspend fun getByMessageId(answerId: String): Answer? {
        return dbQuery {
            AnswerTable.select { AnswerTable.messageId eq answerId }
                .map(AnswerTable::toModel)
                .singleOrNull()
        }
    }

    suspend fun deleteByQuestionId(questionId: String) {
        dbQuery {
            AnswerTable.deleteWhere { AnswerTable.questionId.eq(questionId) }
        }
    }


}
