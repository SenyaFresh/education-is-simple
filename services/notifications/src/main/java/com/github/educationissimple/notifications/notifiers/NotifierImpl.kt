package com.github.educationissimple.notifications.notifiers

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.github.educationissimple.common.Core
import com.github.educationissimple.notifications.R

/**
 * Implementation of the [Notifier] class for creating and displaying reminder notifications.
 * This class specializes in managing notifications for reminders, including channel creation and notification display.
 *
 * @param context The [Context] used to build the notification and access application resources.
 * @param notificationText The text content of the reminder notification.
 * @param notificationManager The [NotificationManager] instance responsible for managing notification channels
 * and displaying notifications.
 */
class NotifierImpl(
    private val context: Context,
    private val notificationText: String,
    notificationManager: NotificationManager
) : Notifier(notificationManager) {

    override val notificationChannelId: String = "app.REMINDER_CHANNEL_ID"

    override val notificationChannelName: String = "app.REMINDER_CHANNEL_NAME"

    override val notificationId: Int = 200

    /**
     * Builds and returns a reminder notification using [NotificationCompat.Builder].
     * The notification includes a title, text, and small icon.
     *
     * @return A [Notification] object configured with reminder details.
     */
    override fun buildNotification(): Notification {
        return NotificationCompat.Builder(context, notificationChannelId)
            .setContentTitle(Core.resources.getString(R.string.reminder))
            .setContentText(notificationText)
            .setSmallIcon(R.drawable.small_notification_icon)
            .build()
    }
}