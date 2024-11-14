package com.github.educationissimple.notifications.notifiers

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.github.educationissimple.common.Core
import com.github.educationissimple.notifications.R

class NotifierImpl(
    private val context: Context,
    private val notificationText: String,
    notificationManager: NotificationManager
) : Notifier(notificationManager) {

    override val notificationChannelId: String = "REMINDER_CHANNEL_ID"
    override val notificationChannelName: String = "Task Reminder"
    override val notificationId: Int = 100

    override fun buildNotification(): Notification {
        return NotificationCompat.Builder(context, notificationChannelId)
            .setContentTitle(Core.resources.getString(R.string.reminder))
            .setContentText(notificationText)
            .setSmallIcon(R.drawable.small_notification_icon)
            .build()
    }
}