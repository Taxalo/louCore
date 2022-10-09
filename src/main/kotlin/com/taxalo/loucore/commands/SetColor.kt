package com.taxalo.loucore.commands

import com.taxalo.loucore.db.MongoDB
import com.taxalo.loucore.utils.Settings.PREFIX
import com.taxalo.loucore.utils.Settings.send
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SetColor : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            when (args.size) {
                0 -> {
                    sender.sendMessage("$PREFIX You need to specify a color")
                }
                1 -> {
                    when (args[0].lowercase()) {
                        "gold" -> {
                            MongoDB.setColor(sender.uniqueId, sender.name, ChatColor.GOLD)
                            sender.sendMessage("$PREFIX Set your name color to: ${ChatColor.GOLD}${sender.name}")
                        }
                        "aqua" -> {
                            MongoDB.setColor(sender.uniqueId, sender.name, ChatColor.AQUA)
                            sender.sendMessage("$PREFIX Set your name color to: ${ChatColor.AQUA}${sender.name}")
                        }
                        "gray" -> {
                            MongoDB.setColor(sender.uniqueId, sender.name, ChatColor.GRAY)
                            sender.sendMessage("$PREFIX Set your name color to: ${ChatColor.GRAY}${sender.name}")
                        }
                        "red" -> {
                            MongoDB.setColor(sender.uniqueId, sender.name, ChatColor.RED)
                            sender.sendMessage("$PREFIX Set your name color to: ${ChatColor.RED}${sender.name}")
                        }
                        "green" -> {
                            MongoDB.setColor(sender.uniqueId, sender.name, ChatColor.GREEN)
                            sender.sendMessage("$PREFIX Set your name color to: ${ChatColor.GREEN}${sender.name}")
                        }
                    }
                }
            }

        } else {
            send("You need to be a Player in order to execute this command.")
        }
        return true
    }
}