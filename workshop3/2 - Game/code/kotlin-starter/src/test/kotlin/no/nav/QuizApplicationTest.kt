package no.nav

import no.nav.rapid.Answer
import no.nav.rapid.Question
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class QuizApplicationTest {
    @Test
    @Disabled
    fun `håndterer team-registration`() {
        val dinHex = "Din-Hex"
        val qa = QuizApplication(dinHex)
        qa.handle(Question(category = "team-registration", question =  "register new team...", created = LocalDateTime.now()))
        val messages = qa.messages()
        assertEquals(1, messages.size)
        assertEquals(dinHex, (messages[0] as Answer).answer)
    }

}
