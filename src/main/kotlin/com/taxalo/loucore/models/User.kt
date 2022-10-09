package com.taxalo.loucore.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class User(
    @Contextual val uuid: UUID,
    val color: String,
    @Contextual val permissions: Map<String, @Contextual PermissionObject>
    )
