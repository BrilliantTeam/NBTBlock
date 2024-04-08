package com.github.l1an.nbtblock.util

import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.block.Block
import org.bukkit.inventory.ItemStack
import taboolib.module.nms.getItemTag
import taboolib.platform.util.bukkitPlugin

/**
 * 返回 NamespacedKey
 */
fun namespacedKey(b: Block) : NamespacedKey {
    return NamespacedKey(bukkitPlugin, "${b.x}_${b.y}_${b.z}")
}

/**
 * 返回 NamespacedKey
 */
fun namespacedKey(l: Location) : NamespacedKey {
    return NamespacedKey(bukkitPlugin, "${l.blockX}_${l.blockY}_${l.blockZ}")
}

/**
 * 返回 NamespacedKey
 */
fun namespacedKeyType(b: Block) : NamespacedKey {
    return NamespacedKey(bukkitPlugin, "${b.x}_${b.y}_${b.z}_type")
}

/**
 * 返回 NamespacedKey
 */
fun namespacedKeyType(l: Location) : NamespacedKey {
    return NamespacedKey(bukkitPlugin, "${l.blockX}_${l.blockY}_${l.blockZ}_type")
}

/**
 * 傳入 ItemTag 後，將其內的鍵值對轉換為 JsonString
 */
fun itemTagToJson(item: ItemStack) : String {
    return item.getItemTag().toJson()
}