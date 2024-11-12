package com.github.educationissimple.glue.audio.mappers

import android.app.Application
import android.content.ContentUris
import android.graphics.Bitmap
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Size
import com.github.educationissimple.audio.domain.entities.Audio
import com.github.educationissimple.audio.domain.entities.AudioListState
import com.github.educationissimple.audio.entities.AudioDataEntity
import com.github.educationissimple.audio_player.entities.AudioItem
import com.github.educationissimple.audio_player.entities.AudioPlayerListState

fun AudioDataEntity.toAudio(): Audio {
    return Audio(
        uri = uri,
        categoryId = null,
        imageBitmap = imageBitmap,
        title = title ?: "Неизвестно",
        subtitle = subtitle ?: "Неизвестно",
        duration = duration ?: 0
    )
}

private val projection: Array<String> = arrayOf(
    MediaStore.Audio.AudioColumns.ARTIST,
    MediaStore.Audio.AudioColumns.DURATION,
    MediaStore.Audio.AudioColumns.TITLE,
    MediaStore.Audio.Albums.ALBUM_ID
)

fun Uri.toAudioDataEntity(application: Application): AudioDataEntity? {
    var audioDataEntity: AudioDataEntity? = null

    val mediaUri = if (DocumentsContract.isDocumentUri(application, this)) {
        val docId = DocumentsContract.getDocumentId(this)
        val mediaId = docId.split(":")[1]
        ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, mediaId.toLong())
    } else {
        this
    }

    application.contentResolver.query(
        mediaUri,
        projection,
        null,
        null,
        null
    )?.use { cursor ->
        if (cursor.moveToFirst()) {
            val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.TITLE)
            val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ARTIST)
            val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DURATION)
            val albumIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM_ID)
            val albumId = cursor.getLong(albumIdColumn)
            val albumBitmap = getAlbumBitmap(application, albumId)

            audioDataEntity = AudioDataEntity(
                uri = mediaUri.toString(),
                imageBitmap = albumBitmap,
                title = cursor.getString(titleColumn),
                subtitle = cursor.getString(artistColumn),
                duration = cursor.getLong(durationColumn),
                categoryId = null
            )
        }
    }

    return audioDataEntity
}

fun getAlbumBitmap(application: Application, albumId: Long): Bitmap? {
    val albumUri = ContentUris.withAppendedId(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, albumId)

    runCatching {
        return application.contentResolver.loadThumbnail(albumUri, Size(480, 480), null)
    }
    return null
}

fun AudioPlayerListState.toAudioListState(): AudioListState {
    return AudioListState(
        state = state.toAudioPlayerListStateState(),
        currentAudioUri = currentAudioUri,
        positionMs = positionMs,
        durationMs = durationMs
    )
}

fun Audio.toAudioItem(): AudioItem {
    return AudioItem(
        uri = uri,
        categoryId = categoryId,
        imageBitmap = imageBitmap,
        title = title,
        subtitle = subtitle,
        duration = duration)
}

fun AudioPlayerListState.State.toAudioPlayerListStateState(): AudioListState.State {
    return when (this) {
        AudioPlayerListState.State.AUDIO_PLAYING -> AudioListState.State.AUDIO_PLAYING
        AudioPlayerListState.State.BUFFERING -> AudioListState.State.BUFFERING
        AudioPlayerListState.State.READY -> AudioListState.State.READY
        AudioPlayerListState.State.IDLE -> AudioListState.State.IDLE
    }
}