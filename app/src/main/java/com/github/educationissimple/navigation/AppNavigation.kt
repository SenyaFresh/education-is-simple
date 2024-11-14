package com.github.educationissimple.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.github.educationissimple.R
import com.github.educationissimple.audio.presentation.components.environment.CurrentAudioFloatingItem
import com.github.educationissimple.audio.presentation.screens.AudioCategoriesScreen
import com.github.educationissimple.audio.presentation.screens.AudioListScreen
import com.github.educationissimple.components.colors.Neutral
import com.github.educationissimple.tasks.presentation.screens.CalendarScreen
import com.github.educationissimple.tasks.presentation.screens.TaskCategoriesScreen
import com.github.educationissimple.tasks.presentation.screens.TaskRemindersScreen
import com.github.educationissimple.tasks.presentation.screens.TasksScreen

@Composable
fun AppNavigation(
    onStartAudioService: () -> Unit,
    onStopAudioService: () -> Unit
) {
    val navController = rememberNavController()
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val titleRes: Int? = when (currentBackStackEntry.value.routeClass()) {
        TasksGraph.TaskCategoriesScreen::class -> R.string.manage_task_categories
        TasksGraph.TasksScreen::class -> R.string.tasks
        TasksGraph.CalendarScreen::class -> R.string.calendar
        TasksGraph.RemindersScreen::class -> R.string.reminders
        AudioGraph.AudioScreen::class -> R.string.audio
        AudioGraph.AudioCategoriesScreen::class -> R.string.manage_audio_categories
        else -> null
    }
    val leftIconAction: IconAction? = if (navController.previousBackStackEntry == null) {
        null
    } else {
        IconAction(Icons.AutoMirrored.Filled.KeyboardArrowLeft) { navController.popBackStack() }
    }
    var tasksScreenSearchEnabled by remember { mutableStateOf(false) }

    val rightIconsActions: List<IconAction>? = when (currentBackStackEntry.value.routeClass()) {
        TasksGraph.TasksScreen::class -> listOf(
            IconAction(
                imageVector = if (!tasksScreenSearchEnabled) Icons.Default.Search else Icons.Default.SearchOff,
                onClick = { tasksScreenSearchEnabled = !tasksScreenSearchEnabled }
            ),
            IconAction(
                imageVector = Icons.Default.CalendarMonth,
                onClick = { navController.navigate(TasksGraph.CalendarScreen) }
            ),
            IconAction(
                imageVector = Icons.Default.Notifications,
                onClick = { navController.navigate(TasksGraph.RemindersScreen) }
            ),
            IconAction(
                imageVector = Icons.Default.Category,
                onClick = { navController.navigate(TasksGraph.TaskCategoriesScreen) }
            )
        )
        AudioGraph.AudioScreen::class -> listOf(
            IconAction(
                imageVector = Icons.Default.Category,
                onClick = { navController.navigate(AudioGraph.AudioCategoriesScreen) }
            )
        )
        else -> null
    }

    Scaffold(
        topBar = {
            AppTopBar(
                titleRes = titleRes,
                leftIconAction = leftIconAction,
                rightIconsActions = rightIconsActions
            )
        },
        bottomBar = {
            Column {
                CurrentAudioFloatingItem(onStopAudioService = onStopAudioService)
                AppNavigationBar(
                    navigationController = navController,
                    tabs = MainTabs
                )
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = TasksGraph,
            enterTransition = { fadeIn(tween(200)) },
            exitTransition = { fadeOut(tween(200)) },
            popEnterTransition = { fadeIn(tween(200)) },
            popExitTransition = { fadeOut(tween(200)) },
            modifier = Modifier.padding(padding)
        ) {
            navigation<TasksGraph>(
                startDestination = TasksGraph.TasksScreen
            ) {
                composable<TasksGraph.TasksScreen> {
                    ShowBackground()
                    TasksScreen(
                        searchEnabled = tasksScreenSearchEnabled,
                        onSearchEnabledChange = { tasksScreenSearchEnabled = it }
                    )
                }
                composable<TasksGraph.TaskCategoriesScreen> {
                    ShowBackground()
                    TaskCategoriesScreen()
                }
                composable<TasksGraph.CalendarScreen> {
                    ShowBackground()
                    CalendarScreen()
                }
                composable<TasksGraph.RemindersScreen> {
                    ShowBackground()
                    TaskRemindersScreen()
                }
            }

            navigation<AudioGraph>(
                startDestination = AudioGraph.AudioScreen
            ) {
                composable<AudioGraph.AudioScreen> {
                    ShowBackground()
                    AudioListScreen(onStartAudioService = onStartAudioService)
                }
                composable<AudioGraph.AudioCategoriesScreen> {
                    ShowBackground()
                    AudioCategoriesScreen()
                }
            }
        }
    }
}

@Composable
fun ShowBackground() {
    Surface(modifier = Modifier.fillMaxSize(), color = Neutral.Light.Lightest) {}
}