package com.github.educationissimple.notifications.receivers

import android.app.AlarmManager
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.github.educationissimple.notifications.notifiers.NotifierImpl

/**
 * A [BroadcastReceiver] to handle alarm events triggered by [AlarmManager].
 * Responsible for generating and displaying notifications for reminders at the scheduled time.
 *
 * This class listens for broadcast intents and uses the provided data to create notifications
 * with the help of a [NotificationManager] and a [NotifierImpl].
 */
class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            val notificationManager =
                it.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notificationText = intent?.getStringExtra(REMINDER_ITEM_TEXT) ?: return
            val notifier = NotifierImpl(
                context = it,
                notificationText = notificationText,
                notificationManager
            )
            notifier.showNotification()
        }
    }

    companion object {
        /**
         * Key used to extract the reminder text from the [Intent].
         */
        const val REMINDER_ITEM_TEXT = "REMINDER_ITEM_TEXT"
    }
}