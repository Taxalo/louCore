package com.taxalo.loucore.utils

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.plugin.java.JavaPlugin

object Settings {

    val PREFIX = "${ChatColor.WHITE}[${ChatColor.GOLD}louChat${ChatColor.WHITE}]${ChatColor.RESET}"
    lateinit var plugin: JavaPlugin
    fun send(s: String) {
        Bukkit.getConsoleSender().sendMessage("$PREFIX $s§r")
    }
}