package com.taxalo.loucore.builders

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class ItemBuilder (material: Material, name: String, vararg lore: String) {
    val item = ItemStack(material, 1)
    init {
        val meta = item.itemMeta
        meta?.setDisplayName(name)
        meta?.lore = listOf(*lore)
        item.itemMeta = meta
    }

}