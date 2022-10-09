package com.taxalo.loucore.events.inventory

import com.taxalo.loucore.builders.DefaultMenu
import com.taxalo.loucore.models.PermissionObject
import com.taxalo.loucore.permissions.PermissionsManager
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import java.time.LocalDateTime

class MenuClickHandler : Listener {

    @EventHandler
    fun onMenuClick(e: InventoryClickEvent) {
        val p = e.whoClicked
        if (e.clickedInventory === p.inventory) return
        if (!DefaultMenu.menus.containsKey(p.uniqueId)) return
        val menu = DefaultMenu.menus[p.uniqueId]
        if (e.clickedInventory !== menu!!.inventories[menu.currentPage]) return
        e.isCancelled = true

        when (e.currentItem) {

            DefaultMenu.previousItem -> {
                if (menu!!.currentPage > 0) {
                    menu.currentPage -= 1
                    DefaultMenu.menus[p.uniqueId] = menu
                    p.openInventory(menu.inventories[menu.currentPage])
                }
            }

            DefaultMenu.nextItem -> {
                if (menu!!.currentPage >= menu.inventories.size - 1) return
                menu.currentPage += 1
                DefaultMenu.menus[p.uniqueId] = menu
                p.openInventory(menu.inventories[menu.currentPage])
            }

        }
        val current = LocalDateTime.now()

        when (e.currentItem!!.type) {
            Material.GREEN_WOOL,
            Material.RED_WOOL -> {

                when (e.click) {

                    ClickType.LEFT -> {
                        e.currentItem!!.type = Material.GREEN_WOOL
                        val permissionObject = PermissionObject(true, p.uniqueId, p.name, current)
                        PermissionsManager.managePermission(
                            e.whoClicked as Player,
                            ChatColor.stripColor(e.currentItem!!.itemMeta!!.displayName).toString(),
                            permissionObject
                        )
                    }

                    ClickType.RIGHT -> {
                        e.currentItem!!.type = Material.RED_WOOL
                        val permissionObject = PermissionObject(false, p.uniqueId, p.name, current)
                        PermissionsManager.managePermission(
                            e.whoClicked as Player,
                            ChatColor.stripColor(e.currentItem!!.itemMeta!!.displayName).toString(),
                            permissionObject
                        )
                    }

                    else -> {}
                }
            }

            else -> {}
        }
    }

}