package com.taxalo.loucore

import com.taxalo.loucore.db.MongoDB
import com.taxalo.loucore.handlers.CommandHandler
import com.taxalo.loucore.handlers.EventHandler
import com.taxalo.loucore.handlers.PermissionsHandler
import com.taxalo.loucore.permissions.PermissionsManager
import com.taxalo.loucore.utils.Settings
import com.taxalo.loucore.utils.Settings.send
import org.bukkit.plugin.java.JavaPlugin
import org.litote.kmongo.serialization.SerializationClassMappingTypeService

class LouCore : JavaPlugin() {
    override fun onEnable() {
        Settings.plugin = this
        System.setProperty("org.litote.mongo.mapping.service", SerializationClassMappingTypeService::class.qualifiedName!!)

        CommandHandler()
        EventHandler()
        PermissionsHandler()
        MongoDB.loadDb()

        send("§aEnabled")
    }

    override fun onDisable() {
        for (player in this.server.onlinePlayers) {
            PermissionsManager.removeAttachment(player)
        }
        send("§cDisabled")
    }

}