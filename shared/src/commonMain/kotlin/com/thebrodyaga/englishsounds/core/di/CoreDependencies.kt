package com.thebrodyaga.englishsounds.core.di

import com.thebrodyaga.englishsounds.core.coroutines.AppDispatchers
import com.thebrodyaga.englishsounds.core.coroutines.AppScope

interface AppDependencies {
    val appScope: AppScope
    val appDispatchers: AppDispatchers
}

interface ActivityDependencies
