package com.afoxxvi.entity

import com.afoxxvi.entity.utils.LocalDateTimeSerializer
import kotlinx.serialization.*
import java.time.LocalDateTime

@Serializable
data class GrafanaAlert(
    val alerts: List<Alert>,
    val commonAnnotations: Map<String, String>,
    val commonLabels: Map<String, String>,
    val externalURL: String,
    val groupKey: String,
    val groupLabels: Map<String, String>,
    val message: String,
    val orgId: Long,
    val receiver: String,
    val state: String,
    val status: String,
    val title: String,
    val truncatedAlerts: Int,
    val version: String
) {
    @Serializable
    data class Alert(
        val annotations: Map<String, String>,
        val dashboardURL: String,
        @Serializable(with = LocalDateTimeSerializer::class)
        val endsAt: LocalDateTime,
        val fingerprint: String,
        val generatorURL: String,
        val labels: Map<String, String>,
        val orgId: Long,
        val panelURL: String,
        val silenceURL: String,
        @Serializable(with = LocalDateTimeSerializer::class)
        val startsAt: LocalDateTime,
        val status: String,
        val valueString: String,
        val values: Map<String, Double>
    )
}