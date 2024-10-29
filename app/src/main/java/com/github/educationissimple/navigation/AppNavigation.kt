package com.github.educationissimple.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.github.educationissimple.R
import com.github.educationissimple.tasks.presentation.screens.CalendarScreen
import com.github.educationissimple.tasks.presentation.screens.CategoriesScreen
import com.github.educationissimple.tasks.presentation.screens.TasksScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val titleRes: Int?
    val topBarVisibility: Boolean
    when (currentBackStackEntry.value.routeClass()) {
        TasksGraph.CategoriesScreen::class -> {
            titleRes = R.string.manage_categories
            topBarVisibility = true
        }

        else -> {
            titleRes = null
            topBarVisibility = false
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                visible = topBarVisibility,
                titleRes = titleRes,
                navigationUpAction = if (navController.previousBackStackEntry == null) {
                    NavigateUpAction.None
                } else {
                    NavigateUpAction.Visible { navController.popBackStack() }
                }
            )
        },
        bottomBar = {
            AppNavigationBar(
                navigationController = navController,
                tabs = MainTabs
            )
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = TasksGraph,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(400)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(400)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    tween(400)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    tween(400)
                )
            },
            modifier = Modifier.padding(padding)
        ) {
            navigation<TasksGraph>(
                startDestination = TasksGraph.TasksScreen,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
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
                startDestination = CalendarGraph.CalendarScreen,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
                ) {
                composable<CalendarGraph.CalendarScreen> {
                    ShowBackground()
                    CalendarScreen()
                }
            }
        }
    }
}

@Composable
fun ShowBackground() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {

    }
}