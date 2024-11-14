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

class NotificationReminderScheduler @Inject constructor(
    private val context: Context
) : ReminderScheduler {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

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

    override fun schedule(item: ReminderItem) {
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            item.datetime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
            createPendingIntent(item)
        )
    }

    override fun cancel(item: ReminderItem) {
        alarmManager.cancel(createPendingIntent(item))
    }

}