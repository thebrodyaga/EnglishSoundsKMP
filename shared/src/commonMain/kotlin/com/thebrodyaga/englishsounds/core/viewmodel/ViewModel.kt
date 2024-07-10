package com.thebrodyaga.englishsounds.core.viewmodel

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.InstanceKeeperOwner
import com.arkivanov.essenty.instancekeeper.getOrCreate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

abstract class ViewModel : InstanceKeeper.Instance {
    protected val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    override fun onDestroy() {
        viewModelScope.cancel()
    }
}

inline fun <reified F : ViewModel> InstanceKeeperOwner.viewModel(factory: () -> F): F =
    instanceKeeper.getOrCreate { factory.invoke() }
