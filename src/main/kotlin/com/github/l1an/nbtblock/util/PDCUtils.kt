package com.github.l1an.nbtblock.util

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
 * 傳入 ItemTag 後，將其內的鍵值對轉換為 JsonString
 */
fun itemTagJson(item: ItemStack) : String {
    return item.getItemTag().toJson()
}