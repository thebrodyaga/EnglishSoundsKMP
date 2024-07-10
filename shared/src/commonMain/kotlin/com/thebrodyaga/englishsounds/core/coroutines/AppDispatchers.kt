package com.thebrodyaga.englishsounds.core.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

interface AppDispatchers {
    val IO: CoroutineDispatcher
    val Default: CoroutineDispatcher
    val Main: CoroutineDispatcher
    val Unconfined: CoroutineDispatcher
}

class DefaultDispatchers(
    override val IO: CoroutineDispatcher = Dispatchers.IO,
    override val Unconfined: CoroutineDispatcher = Dispatchers.Unconfined,
    override val Default: CoroutineDispatcher = Dispatchers.Default,
    override val Main: CoroutineDispatcher = Dispatchers.Main.immediate,
) : AppDispatchers