package com.thebrodyaga.englishsounds.data.sounds.impl

import com.thebrodyaga.englishsounds.data.sounds.api.SoundsVideoRepository
import com.thebrodyaga.englishsounds.data.sounds.api.model.AdvancedExercisesVideoRes
import com.thebrodyaga.englishsounds.data.sounds.api.model.ContrastingSoundVideoRes
import com.thebrodyaga.englishsounds.data.sounds.api.model.MostCommonWordsVideoRes
import com.thebrodyaga.englishsounds.data.sounds.api.model.SoundType
import com.thebrodyaga.englishsounds.data.sounds.api.model.SoundVideoRes
import englishsoundskmp.shared.generated.resources.Res
import englishsoundskmp.shared.generated.resources.advanced_exercises_video
import englishsoundskmp.shared.generated.resources.most_common_words_video
import englishsoundskmp.shared.generated.resources.sound_contrasting_video
import englishsoundskmp.shared.generated.resources.sound_video
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.jetbrains.compose.resources.getStringArray

class SoundsVideoRepositoryImpl constructor(
) : SoundsVideoRepository {

    override fun getSoundsVideo(): Flow<List<SoundVideoRes>> =
        getSoundsVideoFromRes()

    override fun getContrastingSoundsVideo(): Flow<List<ContrastingSoundVideoRes>> =
        getContrastingSoundsVideoFromRes()

    override fun getMostCommonWordsVideo(): Flow<List<MostCommonWordsVideoRes>> =
        getMostCommonWordsVideoFromRes()

    override fun getAdvancedExercisesVideo(): Flow<List<AdvancedExercisesVideoRes>> =
        getAdvancedExercisesVideoFromRes()

    private fun getSoundsVideoFromRes(): Flow<List<SoundVideoRes>> {
        return flow {
            val videoArray = getStringArray(Res.array.sound_video)
            val videoMap = mutableListOf<SoundVideoRes>()
            videoArray.forEach {
                val split = it.split("::")
                val transcription = split.first()
                val soundType: SoundType = when (split[2]) {
                    "consonantSounds" -> SoundType.CONSONANT_SOUND
                    "rControlledVowels" -> SoundType.R_CONTROLLED_VOWELS
                    "vowelSounds" -> SoundType.VOWEL_SOUNDS
                    else -> throw IllegalArgumentException()
                }
                videoMap.add(SoundVideoRes(transcription, split[1], soundType, split[3]))
            }
            emit(videoMap)
        }
    }

    private fun getContrastingSoundsVideoFromRes(): Flow<List<ContrastingSoundVideoRes>> {
        return flow {
            val videoArray = getStringArray(Res.array.sound_contrasting_video)
            val videoList = mutableListOf<ContrastingSoundVideoRes>()
            videoArray.forEach {
                val split = it.split("::")
                videoList.add(ContrastingSoundVideoRes(split[0], split[1], split[2], split[3]))
            }
            emit(videoList)
        }
    }

    private fun getMostCommonWordsVideoFromRes(): Flow<List<MostCommonWordsVideoRes>> {
        return flow {
            val videoArray = getStringArray(Res.array.most_common_words_video)
            val videoList = mutableListOf<MostCommonWordsVideoRes>()
            videoArray.forEach {
                val split = it.split("::")
                videoList.add(MostCommonWordsVideoRes(split[0], split[1]))
            }
            emit(videoList)
        }
    }

    private fun getAdvancedExercisesVideoFromRes(): Flow<List<AdvancedExercisesVideoRes>> {
        return flow {
            val videoArray = getStringArray(Res.array.advanced_exercises_video)
            val videoList = mutableListOf<AdvancedExercisesVideoRes>()
            videoArray.forEach {
                val split = it.split("::")
                videoList.add(
                    AdvancedExercisesVideoRes(
                        split[0],
                        split[1],
                        split.getOrNull(2),
                        split.getOrNull(3)
                    )
                )
            }
            emit(videoList)
        }
    }
}