package com.thebrodyaga.englishsounds

import com.thebrodyaga.englishsounds.data.sounds.api.model.AmericanSoundDto
import englishsoundskmp.shared.generated.resources.Res
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
class Greeting {
    private val platform: Platform = getPlatform()

    fun greet(): String {
        val list = runBlocking {
            val sound = Res.readBytes("files/AmericanSounds/json")
            val withUnknownKeys = Json { ignoreUnknownKeys = true }
            val dto: List<AmericanSoundDto> = withUnknownKeys.decodeFromString(sound.decodeToString())
            dto
        }
        val uri = Res.getUri("files/AmericanSounds/json")

        return "Hello, ${platform.name} \n ${uri}!"
    }
}