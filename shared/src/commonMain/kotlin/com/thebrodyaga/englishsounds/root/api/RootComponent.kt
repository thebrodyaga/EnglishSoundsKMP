@file:OptIn(ExperimentalResourceApi::class)

package com.thebrodyaga.englishsounds.root.api

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnResume
import com.thebrodyaga.englishsounds.data.sounds.api.model.AmericanSoundDto
import englishsoundskmp.shared.generated.resources.Res
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi

class RootComponent(
    rootDependencies: RootDependencies,
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
            rootDependencies.audioPlayer.playAudio(Res.getUri("files/" + list.first().audioPath))
        }
    }
}