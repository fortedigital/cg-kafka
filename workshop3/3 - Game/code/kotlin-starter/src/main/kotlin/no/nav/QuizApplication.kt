package no.nav

import no.nav.db.Database
import no.nav.quizrapid.*
import no.nav.rapid.Answer
import no.nav.rapid.Assessment
import no.nav.rapid.Question

/**
 * QuizApplication
 *
 * Her skal teamet bygge ut funksjonalitet for å løse oppgavene i leesah-game.
 */
class QuizApplication(private val teamName: String, database: Database? = null): QuizParticipant(teamName) {

    override fun handle(question: Question) {
        logger.log(question)
       handleRegisterTeam(question)
    }


    override fun handle(assessment: Assessment) {
        logger.log(assessment)
    }

    override fun handle(answer: Answer) {
        logger.log(answer)
    }

    /**
     * Spørsmål handlers
     */










    private fun handleRegisterTeam(question: Question) {
        // [Question] category: nais-log,
        // question: [NAIS Oppgave] Quizmaster har logget en hemmelig nøkkel for deg i sine logger, klarer du å finne denne nøkkelen og sende den tilbake?,
        // id: 88e9f208-640a-4bfe-a781-fb577ed66c20
        if (question.category == "nais-log") {
            answer(question.category, questionId = question.id(), answer = "" )
        }

        // [Question] category: transactions, question: INNSKUDD 3184, id: f4de5ce4-b06c-4b82-96af-f764abd727db
        // [Question] category: transactions, question: INNSKUDD 3414, id: 2df617d3-69cb-463e-ba45-1181c12c9a3e
        if (question.category == "transactions") {
            answer(question.category, questionId = question.id(), answer = "" )
        }

        //[Question] category: NAV, question: Hvor har vi kontor?, id: 01c57029-c155-45c1-8287-1efe68b069a0
        if (question.id() == "01c57029-c155-45c1-8287-1efe68b069a0") {
            answer(question.category, questionId = question.id(), answer = "Fyrstikkalléen 1")
        }

        //[Question] category: NAV, question: Hvor mye er 1G per 1. mai 2023?, id: c4fc84fc-8b41-4ebf-98a7-5cc1011f30a4
        if (question.id() == "c4fc84fc-8b41-4ebf-98a7-5cc1011f30a4") {
            answer(question.category, questionId = question.id(), answer = "118 620 kroner")
        }

        //[Question] category: NAV, question: Hva heter designsystemet vårt?, id: d0f83c8e-281a-4ca7-bbac-fdbdb4ca4198
        if (question.id() == "d0f83c8e-281a-4ca7-bbac-fdbdb4ca4198") {
            answer(question.category, questionId = question.id(), answer = "Aksel")
        }

        if(question.question.contains("svaret må rundes til int"))
            answer(question.category, questionId = question.id(), answer = regnUt(question.question).toString())

        // [Question] category: NAV, question: Hva heter applikasjonsplattformen til NAV?, id: 0df7552c-ab8e-42cc-bd11-a6670116885c
        if (question.id() == "0df7552c-ab8e-42cc-bd11-a6670116885c") {
            answer(question.category, questionId = question.id(), answer = "NAIS")
        }

        // [Question] category: NAV,
        // question: På hvilken nettside finner man informasjon om rekruttering til NAV IT?,
        // id: d0bcc96d-30ea-4fd7-a03a-95ddf952c8cd
        if (question.id() == "d0bcc96d-30ea-4fd7-a03a-95ddf952c8cd") {
            answer(question.category, questionId = question.id(), answer = "detsombetyrnoe.no")
        }

        // [Question] category: NAV, question: Hva heter NAV-direktøren?, id: eab7f69a-b02a-4e42-9219-b64261ce93ed
        if (question.id() == "eab7f69a-b02a-4e42-9219-b64261ce93ed") {
            answer(question.category, questionId = question.id(), answer = "Hans Christian Holte")
        }

        // [Question] category: is-a-prime, question: Er det et primtall? 47, id: df7d58fb-1ca0-49fc-9dcb-ca17ea8b10ba
        if (question.question.startsWith("Er det et primtall?")) {
            answer(question.category, questionId = question.id(), answer = erDetPrimtall(question.question))
        }
    }

    private fun erDetPrimtall(question: String): String {
        val tall = Integer.parseInt(question.subSequence(20, 22).toString())

            val num = tall
            var flag = false
            for (i in 2..num / 2) {
                // condition for nonprime number
                if (num % i == 0) {
                    flag = true
                    break
                }
            }

            if (!flag)
                return("true")
            else
                return("false")
    }
//[Question] category: arithmetic,
// question: 99 * 63 (svaret må rundes til int),
// id: 70e8d7db-110e-43f1-9b47-4b992196c526



    private fun regnUt(question: String): Int {
        val a = Integer.parseInt(question.subSequence(0, 2).toString().trim())
        val b = Integer.parseInt(question.subSequence(5, 7).toString().trim())

        val regnesymbol = question.subSequence(3, 4);

        return when (regnesymbol) {
            "+" -> a+b
            "-" -> a-b
            "*" -> a*b
            "/" -> a/b
            else -> 0
        }

    }

}

