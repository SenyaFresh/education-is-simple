package com.github.educationissimple.notifications.enities

import java.time.LocalDateTime

/**
 * Represents a single reminder item.
 *
 * @property id Unique identifier for the reminder. Used for database operations.
 * @property text The description or content of the reminder. This field is meant to provide
 * meaningful context or instructions for the reminder.
 * @property datetime The scheduled date and time for the reminder, represented as a [LocalDateTime].
 * This is used to determine when the reminder should be displayed to the user.
 */
data class ReminderItem(
    val id: Long,
    val text: String,
    val datetime: LocalDateTime
)