package com.taxalo.loucore.events.serverplayer

import com.taxalo.loucore.permissions.PermissionsManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class JoinHandler: Listener {
    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        PermissionsManager.addAttachment(e.player)
    }
}