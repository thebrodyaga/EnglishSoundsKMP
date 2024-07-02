package com.thebrodyaga.englishsounds

import englishsoundskmp.shared.generated.resources.Res
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
class Greeting {
    private val platform: Platform = getPlatform()

    fun greet(): String {
        val list = runBlocking {
            val sound = Res.readBytes("files/AmericanSounds/json")
            val withUnknownKeys = Json { ignoreUnknownKeys = true }
            val dto: List<AmericanSoundDto> =
                withUnknownKeys.decodeFromString(sound.decodeToString())
            dto
        }
        val uri = Res.getUri("files/AmericanSounds/json")

        return "Hello, ${platform.name} \n ${uri}!"
    }
}

sealed interface SoundDto

@Serializable
data class AmericanSoundDto constructor(
    val transcription: String,
    val name: String,
    val description: String,
    val audioPath: String,
    val photoPath: String,
    val spellingWordList: List<SpellingWordDto>,
    val soundPracticeWords: SoundPracticeWords,
    val soundType: SoundType,
) : SoundDto

@Serializable
data class SoundPracticeWords constructor(
    val beginningSound: List<PracticeWordDto>,
    val endSound: List<PracticeWordDto>,
    val middleSound: List<PracticeWordDto>,
)

enum class SoundType {
    @SerialName("consonantSounds")
    CONSONANT_SOUND,

    @SerialName("rControlledVowels")
    R_CONTROLLED_VOWELS,

    @SerialName("vowelSounds")
    VOWEL_SOUNDS;
}

@Serializable
data class SpellingWordDto constructor(
    val sound: String,
    val name: String,
    val audioPath: String,
    var transcription: String,
)

@Serializable
data class PracticeWordDto constructor(
    val sound: String,
    val name: String,
    val audioPath: String,
)