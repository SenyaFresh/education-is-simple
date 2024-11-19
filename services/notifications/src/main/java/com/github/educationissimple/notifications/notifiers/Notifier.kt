package com.github.educationissimple.notifications.notifiers

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager

/**
 * Abstract base class for managing and displaying notifications.
 * This class provides methods for creating notification channels and building notifications.
 *
 * @param notificationManager The [NotificationManager] instance used to manage notification channels
 * and display notifications.
 */
abstract class Notifier(
    private val notificationManager: NotificationManager
) {
    /**
     * Unique ID for the notification channel.
     */
    abstract val notificationChannelId: String

    /**
     * Name for the notification channel.
     */
    abstract val notificationChannelName: String

    /**
     * Unique ID for the notification.
     */
    abstract val notificationId: Int

    /**
     * Displays a notification using the [NotificationManager].
     * This method creates a notification channel and shows
     * the notification built by the [buildNotification] method.
     */
    fun showNotification() {
        val channel = createNotificationChannel()
        notificationManager.createNotificationChannel(channel)
        val notification = buildNotification()
        notificationManager.notify(
            notificationId,
            notification
        )
    }

    /**
     * Creates a notification channel with the specified importance.
     *
     * @param importance The importance level of the notification channel.
     * Defaults to [NotificationManager.IMPORTANCE_DEFAULT].
     * @return A [NotificationChannel] instance configured with the given parameters.
     */
    open fun createNotificationChannel(
        importance: Int = NotificationManager.IMPORTANCE_DEFAULT
    ): NotificationChannel {
        return NotificationChannel(
            notificationChannelId,
            notificationChannelName,
            importance
        )
    }

    /**
     * Builds and returns a [Notification] object to be displayed.
     *
     * @return A [Notification] object.
     */
    abstract fun buildNotification(): Notification

}