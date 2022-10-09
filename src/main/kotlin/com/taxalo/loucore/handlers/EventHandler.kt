package com.taxalo.loucore.handlers

import com.taxalo.loucore.events.chat.MessageHandler
import com.taxalo.loucore.events.inventory.MenuClickHandler
import com.taxalo.loucore.events.serverplayer.JoinHandler
import com.taxalo.loucore.events.serverplayer.QuitHandler
import com.taxalo.loucore.utils.Settings


class EventHandler {
    init {
        listOf(
            MessageHandler(),
            JoinHandler(), QuitHandler(),
            MenuClickHandler()
        ).forEach {
            Settings.plugin.server.pluginManager.registerEvents(it, Settings.plugin)
        }
    }
}