package com.afoxxvi

import com.afoxxvi.handler.GrafanaAlertHandler
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        post("/grafana/alert", GrafanaAlertHandler.handler)
    }
}
