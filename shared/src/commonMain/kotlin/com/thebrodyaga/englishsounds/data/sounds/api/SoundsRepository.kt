package com.thebrodyaga.englishsounds.data.sounds.api

import com.thebrodyaga.englishsounds.data.sounds.api.model.AmericanSoundDto
import com.thebrodyaga.englishsounds.data.sounds.api.model.PracticeWordDto
import kotlinx.coroutines.flow.Flow

interface SoundsRepository {

    fun getAllSounds(): Flow<List<AmericanSoundDto>>
    fun getSounds(transcription: String): Flow<AmericanSoundDto>
    fun getAllPracticeWords(): Flow<List<PracticeWordDto>>
}