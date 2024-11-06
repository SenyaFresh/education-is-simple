package com.github.educationissimple.glue.audio.mappers

import android.app.Application
import android.content.ContentUris
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.MediaStore
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import com.github.educationissimple.audio.domain.entities.Audio
import com.github.educationissimple.audio.domain.entities.AudioListState
import com.github.educationissimple.audio.entities.AudioDataEntity
import com.github.educationissimple.audio_player.entities.AudioItem
import com.github.educationissimple.audio_player.entities.AudioPlayerListState

fun AudioDataEntity.toAudio(): Audio {
    return Audio(
        uri = uri,
        categoryId = null,
        imageUri = imageUri,
        title = title ?: "Неизвестно",
        subtitle = subtitle ?: "Неизвестно",
        duration = duration ?: 0
    )
}

private val projection: Array<String> = arrayOf(
    MediaStore.Audio.AudioColumns.ARTIST,
    MediaStore.Audio.AudioColumns.DURATION,
    MediaStore.Audio.AudioColumns.TITLE
)

@OptIn(UnstableApi::class)
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

            audioDataEntity = AudioDataEntity(
                uri = mediaUri.toString(),
                imageUri = "", // todo: добыть изображение
                title = cursor.getString(titleColumn),
                subtitle = cursor.getString(artistColumn),
                duration = cursor.getLong(durationColumn)
            )
        }
    }

    return audioDataEntity
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
        imageUri = imageUri,
        title = title,
        subtitle = subtitle,
        duration = duration)
}

fun AudioPlayerListState.State.toAudioPlayerListStateState(): AudioListState.State {
    return when (this) {
        AudioPlayerListState.State.AUDIO_PLAYING -> AudioListState.State.AUDIO_PLAYING
        AudioPlayerListState.State.BUFFERING -> AudioListState.State.BUFFERING
        AudioPlayerListState.State.PLAYING -> AudioListState.State.PLAYING
        AudioPlayerListState.State.READY -> AudioListState.State.READY
    }
}