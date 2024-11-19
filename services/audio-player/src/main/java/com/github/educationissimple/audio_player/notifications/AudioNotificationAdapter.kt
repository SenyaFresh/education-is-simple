package com.github.educationissimple.audio_player.notifications

import android.app.PendingIntent
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerNotificationManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.github.educationissimple.audio_player.R
import com.github.educationissimple.common.Core
import javax.inject.Inject

/**
 * Adapter for customizing the media description and notification appearance
 * for audio playback notifications managed by [PlayerNotificationManager].
 *
 * @param context The [Context] used for accessing resources and dependencies.
 * @param pendingIntent The [PendingIntent] to be invoked when the notification is clicked.
 *
 * This class implements [PlayerNotificationManager.MediaDescriptionAdapter] to
 * provide custom media metadata, content intent, and artwork for the audio playback notification.
 */
@UnstableApi
class AudioNotificationAdapter @Inject constructor(
    private val context: Context,
    private val pendingIntent: PendingIntent?
) : PlayerNotificationManager.MediaDescriptionAdapter {

    /**
     * Returns the title of the currently playing media for the notification.
     *
     * @param player The [Player] instance providing media playback information.
     * @return A [CharSequence] representing the media title, or a default "Unknown" string if unavailable.
     */
    override fun getCurrentContentTitle(player: Player): CharSequence {
       return player.mediaMetadata.title ?: Core.resources.getString(R.string.unknown)
    }

    /**
     * Provides a [PendingIntent] that will be executed when the user clicks on the notification.
     *
     * @param player The [Player] instance providing media playback information.
     * @return The [PendingIntent] passed to this adapter or `null` if none is provided.
     */
    override fun createCurrentContentIntent(player: Player): PendingIntent? {
        return pendingIntent
    }

    /**
     * Returns the subtitle or author of the currently playing media for the notification.
     *
     * @param player The [Player] instance providing media playback information.
     * @return A [CharSequence] representing the media subtitle.
     */
    override fun getCurrentContentText(player: Player): CharSequence? {
        return player.mediaMetadata.subtitle
    }

    /**
     * Retrieves the large icon (e.g., album artwork) for the currently playing media.
     * The image is loaded asynchronously using Glide and passed to the notification
     * via the provided [PlayerNotificationManager.BitmapCallback].
     *
     * @param player The [Player] instance providing media playback information.
     * @param callback A [PlayerNotificationManager.BitmapCallback] used to provide the icon bitmap asynchronously.
     * @return `null` initially, as the icon is loaded asynchronously.
     */
    override fun getCurrentLargeIcon(
        player: Player,
        callback: PlayerNotificationManager.BitmapCallback
    ): Bitmap? {
        Glide.with(context)
            .asBitmap()
            .load(player.mediaMetadata.artworkUri)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    callback.onBitmap(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) = Unit
            })
        return null
    }

}