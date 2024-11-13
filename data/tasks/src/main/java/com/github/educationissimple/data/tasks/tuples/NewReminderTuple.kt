package com.github.educationissimple.data.tasks.tuples

import java.time.LocalDateTime

data class NewReminderTuple(
    val taskId: Long,
    val datetime: LocalDateTime
)