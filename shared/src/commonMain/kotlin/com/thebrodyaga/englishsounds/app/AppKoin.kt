package com.thebrodyaga.englishsounds.app

import com.thebrodyaga.englishsounds.core.coroutines.AppDispatchers
import com.thebrodyaga.englishsounds.core.coroutines.AppScope
import com.thebrodyaga.englishsounds.core.coroutines.DefaultDispatchers
import com.thebrodyaga.englishsounds.core.di.AppDependencies
import com.thebrodyaga.englishsounds.data.sounds.api.SoundsRepository
import com.thebrodyaga.englishsounds.data.sounds.api.SoundsVideoRepository
import com.thebrodyaga.englishsounds.data.sounds.impl.AmericanSoundsRepositoryImpl
import com.thebrodyaga.englishsounds.data.sounds.impl.SoundsVideoRepositoryImpl
import org.koin.core.Koin
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.ModuleDeclaration
import org.koin.dsl.bind
import org.koin.dsl.koinApplication
import org.koin.dsl.module

fun initAppKoin(
    appDeclaration: KoinAppDeclaration = {},
    moduleDeclaration: ModuleDeclaration = {},
): AppComponent {
    val appKoin = koinApplication {
        modules(mergedAppModule)
        modules(module { moduleDeclaration() })
        appDeclaration()
    }
    return DefaultAppComponent(appKoin = appKoin.koin)
}

private val mergedAppModule = listOf(
    dataSoundsModule,
    coreModule,
)

private val dataSoundsModule
    get() = module {
        singleOf(::AmericanSoundsRepositoryImpl) bind SoundsRepository::class
        singleOf(::SoundsVideoRepositoryImpl) bind SoundsVideoRepository::class
    }

private val coreModule
    get() = module {
        single { DefaultDispatchers() } bind AppDispatchers::class
        singleOf(::AppScope)
    }

interface AppComponent :
    KoinComponent,
    AppDependencies

private class DefaultAppComponent(
    private val appKoin: Koin,
) : AppComponent {
    override fun getKoin(): Koin = appKoin

    override val appScope: AppScope by inject()
    override val appDispatchers: AppDispatchers by inject()
}