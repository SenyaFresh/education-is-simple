package com.github.educationissimple.tasks.presentation.utils

import com.github.educationissimple.common.Core
import com.github.educationissimple.tasks.R
import java.time.Month

/**
 * Converts a [Month] enum value to a string representation, retrieving the localized month name
 * from the app's resources.
 *
 * This function takes a [Month] enum value and returns the corresponding localized string representation
 * of the month by fetching it from the app's resource file.
 * Each month is mapped to its respective string resource (e.g., "January" for [Month.JANUARY]).
 *
 * @param month The [Month] enum value to be converted into a string.
 * @return A string representing the name of the month.
 */
fun monthToString(month: Month): String {
    return when (month) {
        Month.JANUARY -> Core.resources.getString(R.string.january)
        Month.FEBRUARY -> Core.resources.getString(R.string.february)
        Month.MARCH -> Core.resources.getString(R.string.march)
        Month.APRIL -> Core.resources.getString(R.string.april)
        Month.MAY -> Core.resources.getString(R.string.may)
        Month.JUNE -> Core.resources.getString(R.string.june)
        Month.JULY -> Core.resources.getString(R.string.july)
        Month.AUGUST -> Core.resources.getString(R.string.august)
        Month.SEPTEMBER -> Core.resources.getString(R.string.september)
        Month.OCTOBER -> Core.resources.getString(R.string.october)
        Month.NOVEMBER -> Core.resources.getString(R.string.november)
        Month.DECEMBER -> Core.resources.getString(R.string.december)
    }
}