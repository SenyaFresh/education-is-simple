package com.github.educationissimple.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Phone
import androidx.compose.ui.graphics.vector.ImageVector
import com.github.educationissimple.R
import kotlinx.collections.immutable.persistentListOf

data class AppTab(
    val imageVector: ImageVector,
    @StringRes val titleRes: Int,
    val graphRoute: Any
)

val MainTabs = persistentListOf(
    AppTab(
        imageVector = Icons.AutoMirrored.Outlined.List,
        titleRes = R.string.tasks,
        graphRoute = TasksGraph
    ),
    AppTab(
        imageVector = Icons.Default.DateRange,
        titleRes = R.string.calendar,
        graphRoute = CalendarGraph
    ),
    AppTab(
        imageVector = Icons.Default.Phone,
        titleRes = R.string.audio,
        graphRoute = AudioGraph
    )
)