package com.thebrodyaga.englishsounds.app

import com.thebrodyaga.englishsounds.core.di.ActivityDependencies
import com.thebrodyaga.englishsounds.data.sounds.api.SoundsRepository
import com.thebrodyaga.englishsounds.feature.audioPlayer.api.AudioPlayer
import com.thebrodyaga.englishsounds.root.api.RootDependencies
import org.koin.core.Koin
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.ModuleDeclaration
import org.koin.dsl.koinApplication
import org.koin.dsl.module

fun initActivityKoin(
    appComponent: AppComponent,
    appDeclaration: KoinAppDeclaration = {},
    moduleDeclaration: ModuleDeclaration = {},
): ActivityComponent {
    val activityKoin = koinApplication {
        modules(module { moduleDeclaration() })
        appDeclaration()
    }
    return DefaultActivityComponent(activityKoin.koin, appComponent)
}

interface ActivityComponent :
    KoinComponent,
    ActivityDependencies,
    RootDependencies

private class DefaultActivityComponent(
    private val activityKoin: Koin,
    private val appComponent: AppComponent,
) : ActivityComponent {

    override fun getKoin(): Koin = activityKoin

    //from appComponent
    override val soundsRepository: SoundsRepository by appComponent.inject()

    //---------------

    //from activityComponent
    override val audioPlayer: AudioPlayer by activityKoin.inject()
}