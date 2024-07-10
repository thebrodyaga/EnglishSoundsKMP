package com.thebrodyaga.englishsounds.root.api

import com.thebrodyaga.englishsounds.data.sounds.api.SoundsRepository
import com.thebrodyaga.englishsounds.feature.audioPlayer.api.AudioPlayer

interface RootDependencies {
    val soundsRepository: SoundsRepository
    val audioPlayer: AudioPlayer
}