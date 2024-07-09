package com.thebrodyaga.englishsounds.feature.audioPlayer.api

import kotlinx.coroutines.flow.StateFlow

expect interface AudioPlayer {
    fun state(): StateFlow<AudioPlayerState>
    fun stopPlay()
    fun playAudio(audio: String): StateFlow<AudioPlayerState>
}