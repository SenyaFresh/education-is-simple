package com.github.educationissimple.audio.entities

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "audioItems",
)
data class AudioDataEntity(
    @PrimaryKey val uri: String,
    @ColumnInfo(name = "image_uri") val imageBitmap: Bitmap,
    val title: String?,
    val subtitle: String?,
    val duration: Long?
)