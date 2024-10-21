package com.github.educationissimple.glue.tasks.mappers

import java.time.LocalDate

fun LocalDate.toTaskDate(): String? {
    if (this == LocalDate.now()) {
        return null
    }

    val day = if (this.dayOfMonth < 10) "0${this.dayOfMonth}" else this.dayOfMonth
    val month = if (this.monthValue < 10) "0${this.monthValue}" else this.monthValue
    val year = if (year == LocalDate.now().year) "" else this.year

    return if (year == "") "$month-$day" else "$year-$month-$day"

}

fun parseTaskDate(date: String?): LocalDate {
    if (date == null) {
        return LocalDate.now()
    }

    val dateParts = date.split("-")

    if (dateParts.size == 2) {
        return LocalDate.of(LocalDate.now().year, dateParts[0].toInt(), dateParts[1].toInt())
    }

    return LocalDate.of(dateParts[0].toInt(), dateParts[1].toInt(), dateParts[2].toInt())
}