package com.fortedigital.service

import com.fortedigital.config.consumerProps
import com.fortedigital.dto.TeamDTO
import com.fortedigital.repository.*
import com.fortedigital.service.formats.*
import io.ktor.util.logging.*
import kotlinx.serialization.json.Json
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.errors.WakeupException
import java.time.Duration
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean


class KafkaProcessor(private val questionRepository: QuestionRepository, private val answerRepository: AnswerRepository, private val teamRepository: TeamRepository) {
    private val logger = KtorSimpleLogger("KafkaProcessor")
    private val consumer = KafkaConsumer<String, ByteArray>(consumerProps)
    private val closed = AtomicBoolean(false) // Add this line

    private val jsonMapper = Json {
        ignoreUnknownKeys = true
    }

    init {
        Runtime.getRuntime().addShutdownHook(Thread {
            closed.set(true)
            logger.info("Shutting down Kafka consumer")
            consumer.wakeup()
        })
    }

    suspend fun run() {
        logger.info("Starting Kafka consumer")
        try {

            consumer.subscribe(listOf("kafka-game"))
            while (true) {
                val records = consumer.poll(Duration.ofMillis(100))
                for (record in records) {
                    handle(String(record.value()))
                }
                consumer.commitSync()
            }
        } catch (e: WakeupException) {
            // Ignore exception if closing
            if (!closed.get()) throw e
        } finally {
            consumer.close()
        }
    }

    private suspend fun handle(message: String) {
        logger.info("Handling message: $message")
        try {
            val commonObject = jsonMapper.decodeFromString<Common>(message)
            if (commonObject.type == Type.QUESTION) {
                handleQuestion(message)
                return
            }
            logger.info("Handling message of category: ${commonObject.category}")
            when(commonObject.category) {
                Category.TEAM_REGISTRATION -> {
                    handleTeamRegistrationMessage(message)
                }
                Category.PING_PONG -> {
                    handlePingPong(message)
                }
                Category.BASE64 -> {
                    handleBase64(message)
                }
                else -> {
                    logger.error("Unknown category: ${commonObject.category}")
                }
            }
        } catch (e: Exception) {
            logger.error("Error handling message: $message", e)
        }
    }

    private suspend fun handleQuestion(message: String) {
        val question = jsonMapper.decodeFromString<QuestionMessage>(message)
        val byQuestionId = questionRepository.getByQuestionId(question.messageId)
        if (byQuestionId != null) {
            logger.error("Received question exists in db, ignoring")
        } else {
            val newQuestion = Question(0, question.messageId, question.question, question.category, question.created)
            val id = questionRepository.create(newQuestion)
            logger.info("Question created with id: $id")
        }
    }

    private suspend fun handleTeamRegistrationMessage(message: String) {
        val teamRegistration = handleCommon<AnswerMessage>(message) ?: return
        logger.info("Team registration: ${teamRegistration.teamName} - ${teamRegistration.answer}")
        // determine if answer is a hex-color
        val hexColorRegex = Regex("^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$")
        if (!hexColorRegex.matches(teamRegistration.answer)) {
            logger.error("Answer is not a hex-color")
            return
        }
        // check if team already exists
        if (teamRepository.checkIfTeamExists(teamRegistration.teamName)) {
            logger.error("Team already exists")
            return
        }
        // create team
        val team = TeamDTO(0, teamRegistration.teamName, 0, teamRegistration.answer, emptyList())
        val id = teamRepository.create(team)
        logger.info("Team created with id: $id")
    }

    private suspend fun handlePingPong(message: String) {
        val pingPong = handleCommon<AnswerMessage>(message) ?: return
        if (pingPong.answer != "pong") {
            logger.error("Answer is not ping")
            return
        }



        val team = teamRepository.getTeamByName(pingPong.teamName)

        val answer = Answer(
            0,
            team.id,
            Category.PING_PONG.score,
            pingPong.messageId,
            pingPong.questionId,
            pingPong.category,
            pingPong.created,
        )
        answerRepository.create(answer)
    }

    private suspend fun handleBase64(message: String) {
        val base64 = handleCommon<AnswerMessage>(message) ?: return
        // base64 decode answer
        val decodedAnswer = Base64.getDecoder().decode(base64.answer)

        // check if decoded value matches with value in question
        val question = questionRepository.getByQuestionId(base64.questionId)

        val expectedValue = question!!.question.split(" ")[1]
        if (expectedValue != String(decodedAnswer)) {
            logger.error("Answer is not correct")
            return
        }


        val team = teamRepository.getTeamByName(base64.teamName)

        val answer = Answer(
            0,
            team.id,
            Category.BASE64.score,
            base64.messageId,
            base64.questionId,
            base64.category,
            base64.created,
        )
        answerRepository.create(answer)
    }

    private suspend inline fun<reified T: CommonMessage> handleCommon(message: String): T? {
        val answer = jsonMapper.decodeFromString<T>(message)
        val questionsExistsForAnswer = questionsExistsForAnswer(answer.questionId)
        if (!questionsExistsForAnswer) {
            logger.error("Question does not exist for this answer")
            return null
        }

        val byMessageId = answerRepository.getByMessageId(answer.messageId)
        if (byMessageId != null) {
            logger.error("Answer already exists")
            return null
        }

        if (answer !is AnswerMessage) {
            if (!teamRepository.checkIfTeamExists(answer.teamName)) {
                logger.error("Team does not exists")
                return null
            }
        }

        return answer
    }

    private suspend fun questionsExistsForAnswer(questionsId: String): Boolean {
        val byQuestionId = questionRepository.getByQuestionId(questionsId)

        return byQuestionId != null
    }

}