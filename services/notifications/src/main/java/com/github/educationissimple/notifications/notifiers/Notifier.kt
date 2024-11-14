package com.github.educationissimple.notifications.notifiers

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager

abstract class Notifier(
    private val notificationManager: NotificationManager
) {
    abstract val notificationChannelId: String
    abstract val notificationChannelName: String
    abstract val notificationId: Int

    fun showNotification() {
        val channel = createNotificationChannel()
        notificationManager.createNotificationChannel(channel)
        val notification = buildNotification()
        notificationManager.notify(
            notificationId,
            notification
        )
    }

    open fun createNotificationChannel(
        importance: Int = NotificationManager.IMPORTANCE_DEFAULT
    ): NotificationChannel {
        return NotificationChannel(
            notificationChannelId,
            notificationChannelName,
            importance
        )
    }

    abstract fun buildNotification(): Notification

}