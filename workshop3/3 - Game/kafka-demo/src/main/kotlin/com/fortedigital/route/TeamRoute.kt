package com.fortedigital.route

import com.fortedigital.repository.TeamRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureTeamRoute(teamRepository: TeamRepository) {

    routing {
        // List teams
        get("/teams") {
            val teams = teamRepository.list()
                .map { team -> team.toDTO()}
            call.respond(HttpStatusCode.OK, teams)
        }
    }
}
