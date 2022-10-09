package com.taxalo.loucore.permissions

import com.taxalo.loucore.db.MongoDB
import com.taxalo.loucore.models.PermissionObject
import com.taxalo.loucore.utils.Settings
import org.bukkit.entity.Player
import org.bukkit.permissions.PermissionAttachment
import java.util.UUID

object PermissionsManager {
    val attachments = HashMap<UUID, PermissionAttachment>()

    fun managePermission(player: Player, permissionName: String, permissionObject: PermissionObject) {
        if (attachments[player.uniqueId] === null) {
            val newAttachment = player.addAttachment(Settings.plugin)
            newAttachment.setPermission(permissionName, permissionObject.value)
            attachments[player.uniqueId] = newAttachment
        } else {
            attachments[player.uniqueId]!!.setPermission(permissionName, permissionObject.value)
        }
        MongoDB.setPermissions(player.uniqueId, player.name, attachments[player.uniqueId]!!, permissionName, permissionObject)
    }

    fun addAttachment(player: Player) {
        if (attachments[player.uniqueId] != null) return
        val newAttachment = player.addAttachment(Settings.plugin)
        attachments[player.uniqueId] = newAttachment
        MongoDB.loadPermissions(player.uniqueId, newAttachment)
    }

    fun removeAttachment(player: Player) {
        if (attachments[player.uniqueId] === null) return
        player.removeAttachment(attachments[player.uniqueId]!!)
        attachments.remove(player.uniqueId)
    }
}