package com.thebrodyaga.englishsounds.data.sounds.impl

import com.thebrodyaga.englishsounds.data.sounds.api.SoundsRepository
import com.thebrodyaga.englishsounds.data.sounds.api.model.AmericanSoundDto
import com.thebrodyaga.englishsounds.data.sounds.api.model.PracticeWordDto
import englishsoundskmp.shared.generated.resources.Res
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.supervisorScope
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi

class AmericanSoundsRepositoryImpl constructor(
) : SoundsRepository {

    private var loadingSoundsJob: Job? = null

    private val soundsFlow = MutableSharedFlow<List<AmericanSoundDto>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    override fun getAllSounds(): Flow<List<AmericanSoundDto>> = soundsFlow
        .onSubscription { checkIsNeedToLoadSounds() }

    override fun getAllPracticeWords(): Flow<List<PracticeWordDto>> = soundsFlow
        .onSubscription { checkIsNeedToLoadSounds() }
        .map { updatePracticeWords(it) }

    override fun getSounds(transcription: String): Flow<AmericanSoundDto> {
        return getAllSounds()
            .map { it.first { soundDao -> soundDao.transcription == transcription } }
    }

    private suspend fun checkIsNeedToLoadSounds() {
        if (!isSoundsLoaded() && !isSoundsLoading())
            loadSounds()
    }

    private fun isSoundsLoaded() =
        soundsFlow.replayCache.isNotEmpty() && soundsFlow.replayCache.first().isNotEmpty()

    private fun isSoundsLoading() = loadingSoundsJob?.isActive == true

    private suspend fun loadSounds() = supervisorScope {
        val job = async { soundsFromAssets() }
        loadingSoundsJob = job
        soundsFlow.emit(job.await())
    }

    @OptIn(ExperimentalResourceApi::class)
    private suspend fun soundsFromAssets(): List<AmericanSoundDto> {
        val sound = Res.readBytes("files/AmericanSounds/json")
        val withUnknownKeys = Json { ignoreUnknownKeys = true }
        val sounds: List<AmericanSoundDto> = withUnknownKeys.decodeFromString(sound.decodeToString())
        return sounds
    }

    private fun updatePracticeWords(sounds: List<AmericanSoundDto>): List<PracticeWordDto> {
        val result = mutableListOf<PracticeWordDto>()
        sounds.forEach { sound ->
            result.addAll(sound.soundPracticeWords.beginningSound)
            result.addAll(sound.soundPracticeWords.middleSound)
            result.addAll(sound.soundPracticeWords.endSound)
        }
        return result
    }

    companion object {
        private const val jsonPath = "json"
        private const val americanSoundsZip = "AmericanSounds.zip"
        private const val americanSounds = "AmericanSounds"
    }
}
