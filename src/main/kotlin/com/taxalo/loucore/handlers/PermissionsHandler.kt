package com.taxalo.loucore.handlers

import com.taxalo.loucore.permissions.PermissionsManager
import com.taxalo.loucore.utils.Settings

class PermissionsHandler {
    init {
        for (player in Settings.plugin.server.onlinePlayers) {
            PermissionsManager.addAttachment(player)
        }
    }
}