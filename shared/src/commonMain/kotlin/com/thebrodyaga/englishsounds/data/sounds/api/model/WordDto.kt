package com.thebrodyaga.englishsounds.data.sounds.api.model

import kotlinx.serialization.Serializable

@Serializable
data class SpellingWordDto constructor(
    val sound: String,
    val name: String,
    val audioPath: String,
    val transcription: String,
)

@Serializable
data class PracticeWordDto constructor(
    val sound: String,
    val name: String,
    val audioPath: String,
)