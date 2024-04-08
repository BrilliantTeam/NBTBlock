package com.github.l1an.nbtblock.listener

import com.github.l1an.nbtblock.util.itemTagToJson
import com.github.l1an.nbtblock.util.namespacedKey
import com.github.l1an.nbtblock.util.namespacedKeyType
import org.bukkit.Material
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.persistence.PersistentDataType
import taboolib.common.platform.event.SubscribeEvent
import taboolib.module.nms.ItemTag.Companion.fromJson
import taboolib.module.nms.getItemTag
import taboolib.platform.util.buildItem

/**
 * 处理玩家放置和破坏方块的事件
 */
@Suppress("unused")
object PlayerListener {

    @SubscribeEvent(ignoreCancelled = true)
    fun onPlace(e: BlockPlaceEvent) {
        val item = e.itemInHand
        val block = e.block
        // 若該物品沒有ItemTag，則不處理
        if (item.getItemTag().isEmpty()) return

        // 將ItemTag序列化為JsonString後存入pdc
        val chunk = block.chunk
        val pdc = chunk.persistentDataContainer
        pdc.set(namespacedKey(block), PersistentDataType.STRING, itemTagToJson(item))
        // 加个 type 用于在 break 时验证方块类型是否正确
        pdc.set(namespacedKeyType(block), PersistentDataType.STRING, block.type.toString())
    }

    @SubscribeEvent(ignoreCancelled = true)
    fun onBreak(e: BlockBreakEvent) {
        val block = e.block
        // 若該方塊為空氣，則不處理
        if (block.type == Material.AIR) return
        val chunk = block.chunk
        val pdc = chunk.persistentDataContainer
        // 若該chunk內沒有存在該block的key，則不處理
        if (!pdc.has(namespacedKey(block))) return

        // 若该方块的 type 与 pdc 中存储的 type 不一致, 则删除原来的 pdc 并返回
        if (block.type.toString() != pdc.get(namespacedKeyType(block), PersistentDataType.STRING)) {
            pdc.remove(namespacedKey(block))
            pdc.remove(namespacedKeyType(block))
            return
        }

        e.isDropItems = false
        val item = buildItem(block.type)
        val itemTag = item.getItemTag()
        itemTag.putAll(fromJson(pdc.get(namespacedKey(block), PersistentDataType.STRING)!!))
        itemTag.saveTo(item)
        block.world.dropItemNaturally(block.location, item)

        // 處理完畢後刪除pdc中的key
        pdc.remove(namespacedKey(block))
        pdc.remove(namespacedKeyType(block))
    }

}