package com.fortedigital.config

import com.fortedigital.repository.TeamRepository
import com.fortedigital.route.configureTeamRoute
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(teamRepository: TeamRepository, testing: Boolean = false) {
    if (!testing) {
        install(AutoHeadResponse)
        install(CORS) {
            allowMethod(HttpMethod.Options)
            allowMethod(HttpMethod.Put)
            allowMethod(HttpMethod.Delete)
            allowMethod(HttpMethod.Patch)
            allowHeader(HttpHeaders.Authorization)
            allowHeader("MyCustomHeader")
            anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
        }
    }

    configureTeamRoute(teamRepository)
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }
}
