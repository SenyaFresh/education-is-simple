package com.github.educationissimple.notifications.schedulers

import com.github.educationissimple.notifications.enities.ReminderItem

/**
 * Interface for scheduling and canceling [ReminderItem].
 *
 * Designed to be implemented by classes that interact with scheduling systems.
 */
interface ReminderScheduler {

    fun schedule(item: ReminderItem)

    fun cancel(item: ReminderItem)

}