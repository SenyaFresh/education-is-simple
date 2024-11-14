package com.github.educationissimple.notifications.receivers

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.github.educationissimple.notifications.notifiers.NotifierImpl

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
        const val REMINDER_ITEM_TEXT = "REMINDER_ITEM_TEXT"
    }
}