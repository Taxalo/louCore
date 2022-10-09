package com.taxalo.loucore.commands

import com.taxalo.loucore.builders.DefaultMenu
import com.taxalo.loucore.models.PermissionObject
import com.taxalo.loucore.permissions.PermissionsManager
import com.taxalo.loucore.utils.Settings
import com.taxalo.loucore.utils.Settings.PREFIX
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.time.LocalDateTime

class Perms : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        val current = LocalDateTime.now()
        when (args.size) {
            0, 1 -> {
                sender.sendMessage("$PREFIX Specify an user, and an action")
            }
            2 -> {
                val player = Settings.plugin.server.getPlayer(args[1])
                val senderPlayer = sender as Player
                if (player === null) return true
                if (args[0].lowercase() == "list") {
                    val newMenu = DefaultMenu.createMenu(player)
                    if (newMenu != null) {
                        senderPlayer.openInventory(newMenu)
                    }
                } else {
                    sender.sendMessage("$PREFIX Specify an user, an action and a permission to add or remove.")
                }
            }
            3 -> {
                val player = Settings.plugin.server.getPlayer(args[1])!!
                when (args[0].lowercase()) {
                    "add" -> {
                        val senderPlayer = sender as Player
                        val permissionObject = PermissionObject(true, senderPlayer.uniqueId, sender.name, current)
                        PermissionsManager.managePermission(player, args[2], permissionObject)
                        sender.sendMessage("$PREFIX Added permission ${ChatColor.GOLD}${args[2]}${ChatColor.RESET} to ${ChatColor.GOLD}${args[1]}")
                    }
                    "remove" -> {
                        val senderPlayer = sender as Player
                        val permissionObject = PermissionObject(false, senderPlayer.uniqueId, sender.name, current)
                        PermissionsManager.managePermission(player, args[2], permissionObject)
                        sender.sendMessage("$PREFIX Removed permission ${ChatColor.GOLD}${args[2]}${ChatColor.RESET} from ${ChatColor.GOLD}${args[1]}")
                    }
                }
            }
        }
        return true
    }

}