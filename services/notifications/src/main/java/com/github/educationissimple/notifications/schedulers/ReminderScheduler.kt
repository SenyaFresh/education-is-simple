package com.github.educationissimple.notifications.schedulers

import com.github.educationissimple.notifications.enities.ReminderItem

interface ReminderScheduler {

    fun schedule(item: ReminderItem)

    fun cancel(item: ReminderItem)

}