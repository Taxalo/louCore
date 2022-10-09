package com.taxalo.loucore.events.chat

import com.taxalo.loucore.db.MongoDB
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

class MessageHandler : Listener {
    @EventHandler
    fun onMessage(e: AsyncPlayerChatEvent) {
        var newFormat = "${MongoDB.getColor(e.player.uniqueId)}<player> ${ChatColor.DARK_GRAY}: ${ChatColor.WHITE}<message>"
        newFormat = newFormat
            .replace("<player>", "%1\$s")
            .replace("<message>", "%2\$s")
        e.format = newFormat

    }

}