package com.github.educationissimple.tasks.domain.utils

import java.time.LocalDate

/**
 * Extension function for [LocalDate] that converts the date to a string representation
 * in the format "dd-MM-yyyy" or "dd-MM", depending on whether the year is the current year.
 *
 * If the [LocalDate] is today (i.e., the date equals the current date), the function returns `null`.
 * Otherwise, it formats the date in the following way:
 * - If the date is in the current year, it returns a string in the "dd-MM" format (e.g., "20-11").
 * - If the date is not in the current year, it returns a string in the "dd-MM-yyyy" format (e.g., "20-11-2024").
 *
 * @return A string representing the date in "dd-MM-yyyy" or "dd-MM" format, or `null` if the date is today.
 */
fun LocalDate.toTaskDate(): String? {
    if (this == LocalDate.now()) {
        return null
    }

    val day = if (this.dayOfMonth < 10) "0${this.dayOfMonth}" else this.dayOfMonth
    val month = if (this.monthValue < 10) "0${this.monthValue}" else this.monthValue
    val year = if (year == LocalDate.now().year) "" else this.year

    return if (year == "") "$day-$month" else "$day-$month-$year"

}