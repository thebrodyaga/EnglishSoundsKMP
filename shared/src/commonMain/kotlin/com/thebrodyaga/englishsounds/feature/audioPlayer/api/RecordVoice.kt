package com.thebrodyaga.englishsounds.feature.audioPlayer.api

import kotlinx.coroutines.flow.StateFlow

interface RecordVoice {
    fun stopPlayRecord()
    fun playRecord()
    fun clearRecord()
    fun stopRecord()
    fun startRecord()
    val state: StateFlow<RecordState>
}


sealed interface RecordState {
    data object ReadyToRecord : RecordState
    data object Recording : RecordState
    data class Audio(
        val isWhenPlayingChanged: Boolean = true
    ) : RecordState

    data class PlayingAudio(
        val isPlayingChanged: Boolean = true
    ) : RecordState
}