package com.github.educationissimple.data.tasks.utils

import java.time.LocalDate

/**
 * Returns the minimum possible date.
 *
 * @return The minimum date as a [LocalDate].
 */
fun getMinDate(): LocalDate = LocalDate.of(0, 1, 10)

/**
 * Returns the maximum possible date.
 *
 * @return The maximum date as a [LocalDate].
 */
fun getMaxDate(): LocalDate = LocalDate.of(9999, 12, 31)