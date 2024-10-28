package com.github.educationissimple.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.educationissimple.tasks.presentation.screens.CategoriesScreen
import com.github.educationissimple.tasks.presentation.screens.TasksScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = TasksScreen,
        enterTransition = {
            fadeIn(animationSpec = tween(400))
        },
        exitTransition = {
            fadeOut(animationSpec = tween(400))
        }
    ) {
        composable<TasksScreen> {
            TasksScreen(onManageTasksClicked = { navController.navigate(CategoriesScreen) })
        }

        composable<CategoriesScreen> {
            CategoriesScreen()
        }
    }

}