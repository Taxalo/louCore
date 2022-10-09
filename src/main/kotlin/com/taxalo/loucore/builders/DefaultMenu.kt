package com.taxalo.loucore.builders

import com.taxalo.loucore.db.MongoDB
import dev.dbassett.skullcreator.SkullCreator
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.time.format.DateTimeFormatter
import java.util.UUID
import kotlin.math.ceil


object DefaultMenu {
    data class MultiPageMenu(val inventories: ArrayList<Inventory>, var currentPage: Int)

    val menus = HashMap<UUID, MultiPageMenu>()
    val nextItem = createNextItem()
    val previousItem = createPreviousItem()


    private fun createPreviousItem(): ItemStack {
        val skull =
            SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2VlMDkyOTA2MjBjOGIyOTU5MWZkMDAxZTczOTVjZjMyNDY0ZTJmZDRiNTQ1OWZmNzU3ZDBjZDhlMWM3NTJmOCJ9fX0=")
        val skullData = skull.itemMeta
        skullData!!.setDisplayName("${ChatColor.BLACK}${ChatColor.BOLD}Previous Page")
        skull.itemMeta = skullData
        return skull
    }

    private fun createNextItem(): ItemStack {
        val skull =
            SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWU1ZDlkYzk1MTE5NjIyM2FjNzI5NDc4MDNhMTA5Mzk2NWVhODk4NmE5NWJkOWNkNDJlM2QwMDI0MDExM2YxMCJ9fX0=")
        val skullData = skull.itemMeta
        skullData!!.setDisplayName("${ChatColor.GRAY}${ChatColor.BOLD}Next Page")
        skull.itemMeta = skullData
        return skull
    }

    fun createMenu(p: Player): Inventory? {
        val maxSize = 28
        val permissions = MongoDB.userPermissions[p.uniqueId]
        if (permissions === null) return null
        val items = ArrayList<ItemStack>()

        for (permission in permissions) {
            val material = if (permission.value.value) Material.GREEN_WOOL else Material.RED_WOOL
            val current = permission.value.date
            val formatted = current.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
            items.add(
                ItemBuilder(
                    material,
                    "${ChatColor.AQUA}${permission.key}",
                    "",
                    "${ChatColor.GREEN}LEFT CLICK${ChatColor.GRAY} to activate permission",
                    "${ChatColor.RED}RIGHT CLICK${ChatColor.GRAY} to block permission",
                    "",
                    "${ChatColor.GRAY}Added by ${MongoDB.getColor(permission.value.addedByUUID)}${permission.value.addedByName}${ChatColor.GRAY} the ${ChatColor.WHITE}${formatted}"
                ).item
            )
        }


        val backgroundItem = ItemStack(Material.GREEN_STAINED_GLASS_PANE, 1)
        val backgroundItemMeta = backgroundItem.itemMeta!!
        backgroundItemMeta.setDisplayName("§r")
        backgroundItem.itemMeta = backgroundItemMeta

        val inventories = ArrayList<Inventory>()
        val totalInventories = ceil((items.size.toDouble()) / maxSize.toDouble()).toInt()
        for (i in 0 until totalInventories) {
            val newInventory =
                Bukkit.createInventory(null, 54, "${ChatColor.BLACK}${ChatColor.BOLD}${p.name} permissions")
            if (i != 0) {
                newInventory.setItem(45, previousItem)
            } else {
                newInventory.setItem(45, backgroundItem)
            }

            if (totalInventories > 1 && i != (totalInventories - 1)) {
                newInventory.setItem(53, nextItem)
            } else {
                newInventory.setItem(53, backgroundItem)
            }

            val playerSkull = SkullCreator.itemFromUuid(p.uniqueId)
            val skullData = playerSkull.itemMeta
            skullData!!.setDisplayName("${ChatColor.GOLD}${p.player!!.name}")
            skullData.lore = listOf("${ChatColor.GRAY}Player has a total of ${ChatColor.GOLD}${permissions.size}${ChatColor.GRAY} permissions")
            playerSkull.itemMeta = skullData

            newInventory.setItem(0, playerSkull)

            listOf(
                1, 2, 3, 4, 5, 6, 7, 8,
                46, 47, 48, 49, 50, 51, 52,
                9, 18, 27, 36,
                17, 26, 35, 44
            ).forEach {
                newInventory.setItem(it, backgroundItem)
            }

            var value = 0
            for (item in items) {
                if (newInventory.firstEmpty() == 43) {
                    newInventory.addItem(item)
                    value++
                    break
                }
                newInventory.addItem(item)
                value++
            }
            for (j in 0 until value) {
                items.removeAt(0)
            }
            inventories.add(newInventory)
        }

        val newMultiPageInventory = MultiPageMenu(inventories, 0)

        menus[p.uniqueId] = newMultiPageInventory
        return inventories[0]
    }
}