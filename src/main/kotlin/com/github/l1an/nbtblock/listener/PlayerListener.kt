package com.github.l1an.nbtblock.listener

import com.github.l1an.nbtblock.util.itemTagJson
import com.github.l1an.nbtblock.util.namespacedKey
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.persistence.PersistentDataType
import taboolib.common.platform.event.SubscribeEvent
import taboolib.module.nms.ItemTag.Companion.fromJson
import taboolib.module.nms.getItemTag
import taboolib.platform.util.buildItem

@Suppress("unused")
object PlayerListener {

    @SubscribeEvent
    fun onPlace(e: BlockPlaceEvent) {
        val item = e.itemInHand
        val block = e.block
        // 若該物品沒有ItemTag，則不處理
        if (item.getItemTag().isEmpty()) return

        // 將ItemTag序列化為JsonString後存入pdc
        val chunk = block.chunk
        val pdc = chunk.persistentDataContainer
        pdc.set(namespacedKey(block), PersistentDataType.STRING, itemTagJson(item))
    }

    @SubscribeEvent
    fun onBreak(e: BlockBreakEvent) {
        val block = e.block
        val chunk = block.chunk
        val pdc = chunk.persistentDataContainer
        // 若該chunk內沒有存在該block的key，則不處理
        if (!pdc.has(namespacedKey(block))) return

        e.isDropItems = false
        val item = buildItem(block.type)
        val itemTag = item.getItemTag()
        val tag = fromJson(pdc.get(namespacedKey(block), PersistentDataType.STRING)!!)
        itemTag.putAll(tag)
        itemTag.saveTo(item)
        block.world.dropItemNaturally(block.location, item)

        // 處理完畢後刪除pdc中的key
        pdc.remove(namespacedKey(block))
    }

}