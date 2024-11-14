package com.github.educationissimple.notifications.enities

import java.time.LocalDateTime

data class ReminderItem(
    val id: Long,
    val text: String,
    val datetime: LocalDateTime
)