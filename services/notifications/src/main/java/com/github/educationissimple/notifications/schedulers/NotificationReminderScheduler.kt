package com.github.educationissimple.notifications.schedulers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.github.educationissimple.notifications.enities.ReminderItem
import com.github.educationissimple.notifications.receivers.AlarmReceiver
import com.github.educationissimple.notifications.receivers.AlarmReceiver.Companion.REMINDER_ITEM_TEXT
import java.time.ZoneId
import javax.inject.Inject

/**
 * Implementation of the [ReminderScheduler] interface that uses the [AlarmManager]
 * to schedule and cancel [ReminderItem].
 *
 * This class schedules alarms that trigger broadcast events handled by the [AlarmReceiver].
 * Notifications are displayed as reminders when the scheduled time is reached.
 *
 * @param context The [Context] required to access system services and create intents.
 */
class NotificationReminderScheduler @Inject constructor(
    private val context: Context
) : ReminderScheduler {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    /**
     * Schedules a reminder for the specified [ReminderItem].
     *
     * This method uses the [AlarmManager] to set an exact alarm that triggers
     * at the time defined in the [ReminderItem]. The alarm triggers a broadcast
     * event that is handled by the [AlarmReceiver], which displays the notification.
     *
     * @param item The [ReminderItem] containing details of the reminder.
     */
    override fun schedule(item: ReminderItem) {
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            item.datetime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
            createPendingIntent(item)
        )
    }

    /**
     * Cancels a previously scheduled reminder.
     *
     * This method removes the alarm associated with the given [ReminderItem].
     *
     * @param item The [ReminderItem] whose alarm should be canceled.
     */
    override fun cancel(item: ReminderItem) {
        alarmManager.cancel(createPendingIntent(item))
    }

    /**
     * Creates a [PendingIntent] for the given [ReminderItem].
     *
     * The [PendingIntent] is associated with the [AlarmReceiver] and carries the necessary
     * data to identify and display the reminder notification.
     *
     * @param item The [ReminderItem] for which the [PendingIntent] is created.
     * @return The configured [PendingIntent].
     */
    private fun createPendingIntent(item: ReminderItem) : PendingIntent {
        val intent = Intent(context, AlarmReceiver::class.java)

        intent.putExtra(REMINDER_ITEM_TEXT, item.text)
        return PendingIntent.getBroadcast(
            context,
            item.id.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

}