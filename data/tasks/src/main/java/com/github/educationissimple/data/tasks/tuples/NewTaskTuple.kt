package com.github.educationissimple.data.tasks.tuples

import java.time.LocalDate

data class NewTaskTuple(
    val id: Long,
    val text: String,
    val date: LocalDate = LocalDate.now()
)