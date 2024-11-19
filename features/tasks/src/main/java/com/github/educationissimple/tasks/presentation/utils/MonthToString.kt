package com.github.educationissimple.tasks.presentation.utils

import com.github.educationissimple.common.Core
import com.github.educationissimple.tasks.R
import java.time.Month

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