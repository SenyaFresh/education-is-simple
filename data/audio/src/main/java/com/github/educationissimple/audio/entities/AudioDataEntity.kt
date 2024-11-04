package com.github.educationissimple.audio.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "audioItems",
    indices = [
        Index("uri", unique = true)
    ]
)
data class AudioDataEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val uri: String,
    @ColumnInfo(name = "image_res") val imageRes: Int,
    val title: String,
    val subtitle: String,
    val duration: Long
)