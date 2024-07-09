package com.thebrodyaga.englishsounds.root.api

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnResume
import com.thebrodyaga.englishsounds.AmericanSoundDto
import englishsoundskmp.shared.generated.resources.Res
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
class RootComponent(
    platformDependencies: PlatformDependencies,
    componentContext: ComponentContext,
) : Root, ComponentContext by componentContext {

    init {
        this.lifecycle.doOnResume {
            val list = runBlocking {
                val sound = Res.readBytes("files/AmericanSounds/json")
                val withUnknownKeys = Json { ignoreUnknownKeys = true }
                val dto: List<AmericanSoundDto> =
                    withUnknownKeys.decodeFromString(sound.decodeToString())
                dto
            }
            platformDependencies.audioPlayer.playAudio(Res.getUri("files/" + list.first().audioPath))
        }
    }
}