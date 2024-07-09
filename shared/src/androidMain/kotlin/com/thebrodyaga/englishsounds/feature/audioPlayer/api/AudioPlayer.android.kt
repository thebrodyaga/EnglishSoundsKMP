package com.thebrodyaga.englishsounds.feature.audioPlayer.api

import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.common.Player.Listener
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

actual interface AudioPlayer {
    actual fun state(): StateFlow<AudioPlayerState>
    actual fun stopPlay()
    actual fun playAudio(audio: String): StateFlow<AudioPlayerState>
}

class Player(activity: ComponentActivity) : AudioPlayer, DefaultLifecycleObserver {

    private var player = ExoPlayer
        .Builder(activity)
        .build()
    private var currentAudio: String? = null
    private val state = MutableStateFlow<AudioPlayerState>(AudioPlayerState.Idle(""))
    private var listener: Listener? = null

    init {
        activity.lifecycle.addObserver(this)
    }

    override fun playAudio(audio: String): StateFlow<AudioPlayerState> {
        player.stop()
        state.value = AudioPlayerState.Idle(audio)
        currentAudio = audio
        this.listener?.let { player.removeListener(it) }
        val listener = object : Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                if (isPlaying) state.value = AudioPlayerState.Playing(audio)
                else state.value = AudioPlayerState.Idle(audio)
            }
        }
        this.listener = listener
        player.addListener(listener)
        val mediaItem: MediaItem = MediaItem.fromUri(Uri.parse(audio))
        player.setMediaItem(mediaItem);
        player.prepare()
        player.play()
        return state()
    }

    override fun state(): StateFlow<AudioPlayerState> = state.asStateFlow()

    override fun stopPlay() {
        player.stop()
    }

    override fun onPause(owner: LifecycleOwner) {
        stopPlay()
    }
}