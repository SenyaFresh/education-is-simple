package com.github.educationissimple.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FormatListBulleted
import androidx.compose.material.icons.filled.Biotech
import androidx.compose.material.icons.filled.Headset
import androidx.compose.ui.graphics.vector.ImageVector
import com.github.educationissimple.R
import kotlinx.collections.immutable.persistentListOf

/**
 * Data class representing a tab in the application's navigation system.
 *
 * Each tab contains an image, a title (using a string resource ID), and a graph route that
 * determines the navigation flow when the tab is selected.
 *
 * @param imageVector The [ImageVector] representing the icon to be displayed for the tab.
 * @param titleRes The string resource ID that provides the title for the tab.
 * @param graphRoute The route or navigation graph that corresponds to this tab.
 */
data class AppTab(
    val imageVector: ImageVector,
    @StringRes val titleRes: Int,
    val graphRoute: Any
)

val MainTabs = persistentListOf(
    AppTab(
        imageVector = Icons.Default.Biotech,
        titleRes = R.string.news,
        graphRoute = NewsGraph
    ),
    AppTab(
        imageVector = Icons.AutoMirrored.Filled.FormatListBulleted,
        titleRes = R.string.tasks,
        graphRoute = TasksGraph
    ),
    AppTab(
        imageVector = Icons.Default.Headset,
        titleRes = R.string.audio,
        graphRoute = AudioGraph
    )
)