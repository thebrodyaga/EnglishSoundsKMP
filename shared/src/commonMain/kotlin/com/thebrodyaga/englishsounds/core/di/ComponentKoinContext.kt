package com.thebrodyaga.englishsounds.core.di

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.InstanceKeeperOwner
import com.arkivanov.essenty.instancekeeper.getOrCreate
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.module.Module
import org.koin.dsl.koinApplication

private class ComponentKoinContext : InstanceKeeper.Instance {
    private var koinApp: KoinApplication? = null

    fun getOrCreateKoinScope(modules: List<Module>): Koin {
        if (koinApp == null) {
            koinApp = koinApplication { modules(modules) }
        }
        return requireNotNull(koinApp).koin
    }

    override fun onDestroy() {
        koinApp?.close()
        koinApp = null
    }
}

private val InstanceKeeperOwner.koinContext: ComponentKoinContext
    get() = instanceKeeper.getOrCreate { ComponentKoinContext() }

fun InstanceKeeperOwner.koinScope(list: List<Module> = listOf()): Koin {
    return koinContext.getOrCreateKoinScope(list)
}


