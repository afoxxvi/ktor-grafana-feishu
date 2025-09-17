package com.afoxxvi.handler

import com.afoxxvi.config.Feishu
import com.afoxxvi.entity.GrafanaAlert
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.jackson.jackson
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.*
import java.time.LocalDateTime

object GrafanaAlertHandler {
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            jackson {
                enable(SerializationFeature.INDENT_OUTPUT)
            }
        }
    }

    val handler: suspend (RoutingContext.() -> Unit) = {
        val grafanaAlert = call.receive<GrafanaAlert>()
        val title = grafanaAlert.title
        val message = grafanaAlert.message
        val sendUrl = Feishu.webhookUrl
        val feishuMessage = Feishu.createMessage(
            mapOf(
                "title" to title,
                "level" to "WARN",
                "datetime" to LocalDateTime.now().toString(),
                "message" to message,
                "content" to "",
            )
        )
        // Send to Feishu
        val resp = client.post(sendUrl) {
            contentType(ContentType.Application.Json)
            setBody(feishuMessage)
        }
        call.respond(resp.bodyAsText())
    }
}