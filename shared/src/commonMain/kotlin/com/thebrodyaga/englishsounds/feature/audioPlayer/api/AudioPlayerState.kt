package com.thebrodyaga.englishsounds.feature.audioPlayer.api


sealed interface AudioPlayerState {
    val audioFile: String

    data class Idle(
        override val audioFile: String,
    ) : AudioPlayerState

    data class Playing(
        override val audioFile: String,
    ) : AudioPlayerState
}