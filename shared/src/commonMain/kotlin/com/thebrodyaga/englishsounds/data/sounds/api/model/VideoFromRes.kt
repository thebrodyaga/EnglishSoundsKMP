package com.thebrodyaga.englishsounds.data.sounds.api.model

data class SoundVideoRes constructor(
    val transcription: String,
    val videoId: String,
    val soundType: SoundType,
    val videoName: String
) : VideoFromRes

data class ContrastingSoundVideoRes constructor(
    val firstTranscription: String,
    val secondTranscription: String,
    val videoId: String,
    val videoName: String
) : VideoFromRes

data class MostCommonWordsVideoRes constructor(
    val videoId: String,
    val videoName: String
) : VideoFromRes

data class AdvancedExercisesVideoRes constructor(
    val videoId: String,
    val videoName: String,
    val firstTranscription: String?,
    val secondTranscription: String?
) : VideoFromRes

sealed interface VideoFromRes