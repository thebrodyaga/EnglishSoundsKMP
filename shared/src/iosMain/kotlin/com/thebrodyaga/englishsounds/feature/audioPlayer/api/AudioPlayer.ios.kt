package com.thebrodyaga.englishsounds.feature.audioPlayer.api

import kotlinx.coroutines.flow.StateFlow

actual interface AudioPlayer {
    actual fun state(): StateFlow<AudioPlayerState>
    actual fun stopPlay()
    actual fun playAudio(audio: String): StateFlow<AudioPlayerState>
}