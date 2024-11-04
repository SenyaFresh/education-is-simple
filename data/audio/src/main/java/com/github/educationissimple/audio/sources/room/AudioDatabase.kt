package com.github.educationissimple.audio.sources.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.educationissimple.audio.entities.AudioDataEntity

@Database(
    version = 1,
    entities = [AudioDataEntity::class]
)
abstract class AudioDatabase: RoomDatabase() {

    abstract fun getAudioDao(): AudioDao

}