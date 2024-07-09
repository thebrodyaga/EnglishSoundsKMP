package com.thebrodyaga.englishsounds.root.api

import com.thebrodyaga.englishsounds.feature.audioPlayer.api.AudioPlayer

interface PlatformDependencies {
    val audioPlayer: AudioPlayer
}