package com.afoxxvi.config

import io.ktor.server.config.ApplicationConfig

object Feishu {
    var webhookUrl = ""
        private set
    var templateId = ""
        private set
    var templateVersionName = ""
        private set

    fun init(config: ApplicationConfig) {
        val feishuConfig = config.config("feishu")
        webhookUrl = feishuConfig.property("webhook-url").getString()
        templateId = feishuConfig.property("template-id").getString()
        templateVersionName = feishuConfig.property("template-version-name").getString()
    }

    fun createMessage(variables: Map<String, String>): Map<String, Any> {
        return mapOf(
            "msg_type" to "interactive",
            "card" to mapOf(
                "type" to "template",
                "data" to mapOf(
                    "template_id" to templateId,
                    "template_version_name" to templateVersionName,
                    "template_variable" to variables
                )
            )
        )
    }
}