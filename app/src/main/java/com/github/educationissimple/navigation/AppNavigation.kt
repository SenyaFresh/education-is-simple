package com.github.educationissimple.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.github.educationissimple.R
import com.github.educationissimple.audio.presentation.components.environment.CurrentAudioFloatingItem
import com.github.educationissimple.audio.presentation.screens.AudioListScreen
import com.github.educationissimple.components.colors.Neutral
import com.github.educationissimple.tasks.presentation.screens.CalendarScreen
import com.github.educationissimple.tasks.presentation.screens.CategoriesScreen
import com.github.educationissimple.tasks.presentation.screens.TasksScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val titleRes: Int? = when (currentBackStackEntry.value.routeClass()) {
        TasksGraph.CategoriesScreen::class -> R.string.manage_categories
        TasksGraph.TasksScreen::class -> R.string.tasks
        CalendarGraph.CalendarScreen::class -> R.string.calendar
        AudioGraph.AudioScreen::class -> R.string.audio
        else -> null
    }

    Scaffold(
        topBar = {
            AppTopBar(
                titleRes = titleRes,
                leftIconAction = if (navController.previousBackStackEntry == null) {
                    LeftIconAction.None
                } else {
                    LeftIconAction.Visible(Icons.AutoMirrored.Filled.KeyboardArrowLeft) { navController.popBackStack() }
                }
            )
        },
        bottomBar = {
            Column {
                CurrentAudioFloatingItem()
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
                    TasksScreen(onManageTasksClicked = { navController.navigate(TasksGraph.CategoriesScreen) })
                }
                composable<TasksGraph.CategoriesScreen> {
                    ShowBackground()
                    CategoriesScreen()
                }
            }
            navigation<CalendarGraph>(
                startDestination = CalendarGraph.CalendarScreen
            ) {
                composable<CalendarGraph.CalendarScreen> {
                    ShowBackground()
                    CalendarScreen()
                }
            }

            navigation<AudioGraph>(
                startDestination = AudioGraph.AudioScreen
            ) {
                composable<AudioGraph.AudioScreen> {
                    ShowBackground()
                    AudioListScreen()
                }
            }
        }
    }
}

@Composable
fun ShowBackground() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Neutral.Light.Lightest
    ) {

    }
}