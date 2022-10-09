package com.taxalo.loucore.handlers

import com.taxalo.loucore.commands.Perms
import com.taxalo.loucore.commands.SetColor
import com.taxalo.loucore.utils.Settings


class CommandHandler {
    init {
        listOf(
            Perms(),
            SetColor()
        ).forEach {
            Settings.plugin.getCommand(it::class.simpleName!!.lowercase())!!.setExecutor(it)
        }
    }

}