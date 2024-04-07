package com.github.l1an.nbtblock

import taboolib.common.platform.Plugin
import taboolib.common.platform.function.console
import taboolib.common.platform.function.pluginVersion
import taboolib.module.chat.colored
import taboolib.module.lang.Language

@Suppress("unused")
object NBTBlock : Plugin() {

    const val NBTBLOCK_PREFIX = "&f[ &3NBTBlock &f]"

    override fun onEnable() {

        console().sendMessage("$NBTBLOCK_PREFIX &aNBTBlock has been loaded! - $pluginVersion".colored())
        console().sendMessage("$NBTBLOCK_PREFIX &bAuthor by L1An".colored())

    }
}