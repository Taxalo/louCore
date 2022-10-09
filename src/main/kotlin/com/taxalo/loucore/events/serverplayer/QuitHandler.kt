package com.taxalo.loucore.events.serverplayer

import com.taxalo.loucore.permissions.PermissionsManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class QuitHandler: Listener {

    @EventHandler
    fun onPlayerQuit(e: PlayerQuitEvent) {
        PermissionsManager.removeAttachment(e.player)
    }

}