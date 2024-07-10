package com.thebrodyaga.englishsounds.data.sounds.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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