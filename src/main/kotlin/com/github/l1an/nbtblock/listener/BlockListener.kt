package com.github.l1an.nbtblock.listener

import com.github.l1an.nbtblock.util.namespacedKey
import com.github.l1an.nbtblock.util.namespacedKeyType
import org.bukkit.Material
import org.bukkit.block.PistonMoveReaction
import org.bukkit.event.block.BlockExplodeEvent
import org.bukkit.event.block.BlockPistonExtendEvent
import org.bukkit.event.block.SculkBloomEvent
import org.bukkit.event.entity.EntityExplodeEvent
import org.bukkit.persistence.PersistentDataType
import taboolib.common.platform.event.SubscribeEvent
import taboolib.module.nms.ItemTag.Companion.fromJson
import taboolib.module.nms.getItemTag
import taboolib.platform.util.buildItem
import java.lang.NullPointerException

/**
 * 对于方块被破坏等事件的处理
 */
@Suppress("unused")
object BlockListener {

    /**
     * 当方块爆炸时
     */
    @SubscribeEvent
    fun onBlockExplosion(e: BlockExplodeEvent) {
        val blockList = e.blockList()
        // 对每个被爆炸的方块处理, 检查对应方块是否存在 pdc, 若有则删除，无则不处理
        blockList.forEach {
            val chunk = it.chunk
            val pdc = chunk.persistentDataContainer
            if (pdc.has(namespacedKey(it))) pdc.remove(namespacedKey(it))
        }
    }

    /**
     * 当生物爆炸时
     */
    @SubscribeEvent
    fun onEntityExplosion(e: EntityExplodeEvent) {
        val blockList = e.blockList()
        // 对每个被爆炸的方块处理, 检查对应方块是否存在 pdc, 若有则删除，无则不处理
        blockList.forEach {
            val chunk = it.chunk
            val pdc = chunk.persistentDataContainer
            if (pdc.has(namespacedKey(it))) pdc.remove(namespacedKey(it))
        }
    }

    /**
     * 当方块被活塞推动时
     */
    @SubscribeEvent
    fun onPistonPush(e: BlockPistonExtendEvent) {
        val blockList = e.blocks
        val direction = e.direction
        // 对每个被推动的方块处理, 检查对应方块是否存在 pdc, 若有则删除，无则不处理
        blockList.forEach {
            val pdc = it.chunk.persistentDataContainer

            // 若 reaction 为 BREAK, 且存在 pdc, 则进行破坏并掉落物品的处理
            val reaction = it.pistonMoveReaction
            if (reaction == PistonMoveReaction.BREAK && pdc.has(namespacedKey(it))) {
                val type = it.type
                // 将方块type更改为AIR，然后构建原物品的type并掉落
                it.type = Material.AIR
                val item = buildItem(type)
                val itemTag = item.getItemTag()
                itemTag.putAll(fromJson(pdc.get(namespacedKey(it), PersistentDataType.STRING)!!))
                itemTag.saveTo(item)
                it.world.dropItemNaturally(it.location, item)
                pdc.remove(namespacedKey(it))
                return@forEach
            }

            // 上面的 reaction 若没有触发，则只需删除 pdc 即可
            if (pdc.has(namespacedKey(it))) {
                val newLocation = it.location.clone().add(direction.modX.toDouble(), direction.modY.toDouble(), direction.modZ.toDouble())
                pdc.set(namespacedKey(newLocation), PersistentDataType.STRING, pdc.get(namespacedKey(it), PersistentDataType.STRING)!!)
                pdc.set(namespacedKeyType(newLocation), PersistentDataType.STRING, pdc.get(namespacedKeyType(it), PersistentDataType.STRING)!!)
                pdc.remove(namespacedKey(it))
            }
        }
    }

    /**
     * 当被sculk更改时
     */
    @SubscribeEvent
    fun onSculkBloom(e: SculkBloomEvent) {
        // 获取被sculk转换的方块，检查对应方块是否存在 pdc, 若有则删除，无则不处理
        val block = e.block
        val chunk = try {
            block.chunk
        } catch (e: NullPointerException) {
            return
        }
        val pdc = chunk.persistentDataContainer
        if (pdc.has(namespacedKey(block))) pdc.remove(namespacedKey(block))
    }

}