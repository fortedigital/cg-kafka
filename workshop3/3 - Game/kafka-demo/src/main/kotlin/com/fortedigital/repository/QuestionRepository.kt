package com.fortedigital.repository

import org.jetbrains.exposed.dao.id.IntIdTable
import com.fortedigital.repository.DatabaseFactory.Companion.dbQuery
import com.fortedigital.service.formats.Category
import kotlinx.datetime.Instant
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp


class Question (
    val id: Int,
    val messageId: String,
    val question: String,
    val category: Category,
    val created: Instant
)  {
}
class QuestionRepository {
    private object QuestionTable : IntIdTable() {
        val questionId = varchar("question_id", 64)
        val question = varchar("question", 256)
        val category = enumeration<Category>("category")
        val created = timestamp("created")

        fun toModel(it: ResultRow) = Question(
            it[id].value,
            it[questionId],
            it[question],
            it[category],
            it[created],
        )
    }

    suspend fun create(q: Question): Int = dbQuery {
        QuestionTable.insertAndGetId {
            it[questionId] = q.messageId
            it[question] = q.question
            it[category] = q.category
            it[created] = q.created
        }.value
    }


    // get by questionId
    suspend fun getByQuestionId(questionId: String): Question? {
        return dbQuery {
            QuestionTable.select { QuestionTable.questionId eq questionId }
                .map(QuestionTable::toModel)
                .singleOrNull()
        }
    }

    suspend fun getPreviousQuestions(question: Question): List<Question> {
        return dbQuery {
            QuestionTable.select { QuestionTable.created.less(question.created) and QuestionTable.category.eq(question.category) }
                .map(QuestionTable::toModel)
        }
    }
}
