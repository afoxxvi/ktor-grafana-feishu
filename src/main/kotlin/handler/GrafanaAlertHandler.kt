package com.afoxxvi.handler

import com.afoxxvi.config.Feishu
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
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
        val payload = call.receive<Map<String, Any>>()
        println(payload)
        val title = payload["title"]?.toString() ?: "No Title"
        val message = payload["message"]?.toString() ?: "No Message"
        val sendUrl = Feishu.webhookUrl
        val feishuMessage = Feishu.createMessage(
            mapOf(
                "title" to title,
                "level" to "WARN",
                "datetime" to LocalDateTime.now().toString(),
                "message" to message,
                "content" to payload.toString()
            )
        )
        // Send to Feishu
        val resp = client.post(sendUrl) {
            contentType(ContentType.Application.Json)
            setBody(feishuMessage)
        }
        println("Sent to Feishu: ${resp.status}")
        call.respond(resp)
    }
}