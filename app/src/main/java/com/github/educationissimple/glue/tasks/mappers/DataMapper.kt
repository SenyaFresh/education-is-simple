package com.github.educationissimple.glue.tasks.mappers

import java.time.LocalDate

fun LocalDate.toTaskDate(): String {
    val day = if (this.dayOfMonth < 10) "0${this.dayOfMonth}" else this.dayOfMonth
    val month = if (this.monthValue < 10) "0${this.monthValue}" else this.monthValue
    val year = if (year == LocalDate.now().year) "" else this.year

    return if (year == "") "$month-$day" else "$year-$month-$day"

}