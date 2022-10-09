package com.taxalo.loucore.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.util.UUID

@Serializable
data class PermissionObject(
    val value: Boolean,
    @Contextual val addedByUUID: UUID,
    val addedByName: String,
    @Contextual val date: LocalDateTime
)
