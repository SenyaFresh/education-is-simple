package com.github.educationissimple.audio.sources.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.educationissimple.audio.converters.BitmapConverter
import com.github.educationissimple.audio.entities.AudioCategoryDataEntity
import com.github.educationissimple.audio.entities.AudioDataEntity

@Database(
    version = 1,
    entities = [AudioDataEntity::class, AudioCategoryDataEntity::class]
)
@TypeConverters(BitmapConverter::class)
abstract class AudioDatabase: RoomDatabase() {

    abstract fun getAudioDao(): AudioDao

    abstract fun getAudioCategoryDao(): AudioCategoryDao

}