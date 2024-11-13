package com.github.educationissimple.tasks.domain.entities

import java.time.LocalDateTime

data class TaskReminder(
    val id: Long = 0,
    val taskId: Long,
    val taskText: String,
    val datetime: LocalDateTime
)