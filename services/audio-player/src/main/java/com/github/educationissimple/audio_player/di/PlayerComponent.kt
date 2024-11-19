package com.github.educationissimple.audio_player.di

import android.content.Context
import androidx.media3.session.MediaSession
import com.github.educationissimple.audio_player.handlers.AudioListPlayerHandler
import com.github.educationissimple.audio_player.notifications.AudioNotification
import com.github.educationissimple.audio_player.services.AudioServiceManager
import com.github.educationissimple.common.di.Service
import dagger.BindsInstance
import dagger.Component

/**
 * A Dagger component for managing player-related dependencies in the audio-player module.
 * This component provides instances of [AudioServiceManager], [AudioListPlayerHandler],
 * [MediaSession] and [AudioNotification].
 */
@Service
@Component(modules = [PlayerModule::class])
interface PlayerComponent {

    /**
     * Provides an instance of [AudioServiceManager] .
     *
     * @return The [AudioServiceManager] implementation.
     */
    fun audioServiceManager(): AudioServiceManager

    /**
     * Provides an instance of [AudioListPlayerHandler].
     *
     * @return The [AudioListPlayerHandler] implementation.
     */
    fun playerListHandler(): AudioListPlayerHandler

    /**
     * Provides an instance of [MediaSession].
     *
     * @return The [MediaSession] instance.
     */
    fun mediaSession(): MediaSession

    /**
     * Provides an instance of [AudioNotification].
     *
     * @return The [AudioNotification] implementation.
     */
    fun notification(): AudioNotification

    /**
     * Builder interface for creating instances of [PlayerComponent].
     *
     * Requires [PlayerDeps] provided by [deps] function for providing external dependencies.
     */
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun deps(deps: PlayerDeps): Builder

        fun build(): PlayerComponent
    }
}

/**
 * Interface representing the external dependencies required by the [PlayerComponent].
 * These dependencies are provided at runtime.
 */
interface PlayerDeps {
    val context: Context
}