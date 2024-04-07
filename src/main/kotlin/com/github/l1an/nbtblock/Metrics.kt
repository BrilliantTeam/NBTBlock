package com.github.l1an.nbtblock

import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.Platform
import taboolib.common.platform.function.pluginVersion
import taboolib.module.metrics.Metrics
import taboolib.module.metrics.charts.SingleLineChart

@Suppress("unused")
object Metrics {

    lateinit var metrics: Metrics
        private set

    @Awake(LifeCycle.ACTIVE)
    private fun init() {
        metrics = Metrics(21536, pluginVersion, Platform.BUKKIT)
    }

}