package com.github.educationissimple.audio_player.di

import android.content.Context
import androidx.annotation.OptIn
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.session.MediaSession
import com.github.educationissimple.audio_player.handlers.AudioListPlayerHandler
import com.github.educationissimple.audio_player.handlers.RealAudioListPlayerHandler
import com.github.educationissimple.audio_player.notifications.AudioNotification
import com.github.educationissimple.audio_player.notifications.AudioNotificationImpl
import com.github.educationissimple.audio_player.services.AudioServiceImpl
import com.github.educationissimple.audio_player.services.AudioServiceManager
import com.github.educationissimple.common.di.Service
import dagger.Binds
import dagger.Module
import dagger.Provides

/**
 * Main module for audio-player module.
 */
@Module(includes = [NotificationModule::class, AudioPlayerHandlerModule::class, ServiceModule::class])
class PlayerModule {

    /**
     * Provides [Context] for audio-player module.
     * @param deps The [PlayerDeps] for [PlayerComponent].
     */
    @Provides
    @Service
    fun provideContext(deps: PlayerDeps): Context = deps.context

    /**
     * Provides [AudioAttributes] for audio-player module with [C.AUDIO_CONTENT_TYPE_SPEECH] content type
     * and [C.USAGE_MEDIA] usage.
     */
    @Provides
    @Service
    fun provideAudioAttributes(): AudioAttributes = AudioAttributes.Builder()
        .setContentType(C.AUDIO_CONTENT_TYPE_SPEECH)
        .setUsage(C.USAGE_MEDIA)
        .build()

    /**
     * Provides [ExoPlayer] for audio-player module.
     *
     * @param context The [Context] needed for [ExoPlayer.Builder].
     * @param audioAttributes The [AudioAttributes] needed for [ExoPlayer.Builder].
     */
    @Provides
    @Service
    @UnstableApi
    fun provideExoPlayer(
        context: Context,
        audioAttributes: AudioAttributes,
    ): ExoPlayer = ExoPlayer.Builder(context)
        .setAudioAttributes(audioAttributes, true)
        .setHandleAudioBecomingNoisy(true)
        .setTrackSelector(DefaultTrackSelector(context))
        .build()

    /**
     * Provides [MediaSession] for audio-player module.
     *
     * @param context The [Context] needed for [MediaSession.Builder]
     * @param player The [ExoPlayer] needed for [MediaSession.Builder]
     */
    @Provides
    @Service
    fun provideMediaSession(
        context: Context,
        player: ExoPlayer,
    ): MediaSession = MediaSession.Builder(context, player).build()
}

/**
 * Notification module for audio-player module.
 */
@Module
abstract class NotificationModule {

    @OptIn(UnstableApi::class)
    @Binds
    @Service
    abstract fun provideNotificationManager(audioNotificationImpl: AudioNotificationImpl): AudioNotification

}

/**
 * Service module for audio-player module.
 */
@Module
abstract class ServiceModule {

    @Binds
    @Service
    abstract fun provideServiceManager(serviceManager: AudioServiceImpl): AudioServiceManager

}

/**
 * Playlist handler module for audio-player module.
 */
@Module
abstract class AudioPlayerHandlerModule {

    @Binds
    @Service
    abstract fun bindAudioListPlayerHandler(realAudioListPlayerHandler: RealAudioListPlayerHandler): AudioListPlayerHandler

}