package com.github.educationissimple.audio.sources

import android.content.Context
import androidx.room.Room
import com.github.educationissimple.audio.entities.AudioDataEntity
import com.github.educationissimple.audio.sources.room.AudioDatabase
import javax.inject.Inject

class RoomAudioDataSource @Inject constructor(
    context: Context
) : AudioDataSource {

    private val db: AudioDatabase by lazy<AudioDatabase> {
        Room.databaseBuilder(
            context,
            AudioDatabase::class.java,
            "audio.db"
        )
            .build()
    }

    private val audioDao = db.getAudioDao()

    override suspend fun getAudio(): List<AudioDataEntity> {
        return audioDao.getAudio()
    }

    override suspend fun addAudio(audio: AudioDataEntity) {
        audioDao.addAudio(audio)
    }

    override suspend fun deleteAudio(uri: String) {
        audioDao.deleteAudio(uri)
    }
}