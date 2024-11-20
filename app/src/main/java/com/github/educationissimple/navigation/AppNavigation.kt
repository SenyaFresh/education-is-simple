package com.github.educationissimple.navigation

import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.work.BackoffPolicy
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.github.educationissimple.R
import com.github.educationissimple.audio.presentation.components.environment.CurrentAudioFloatingItem
import com.github.educationissimple.audio.presentation.screens.AudioCategoriesScreen
import com.github.educationissimple.audio.presentation.screens.AudioListScreen
import com.github.educationissimple.common.Core
import com.github.educationissimple.news.presentation.screens.NewsScreen
import com.github.educationissimple.sync.RemindersSyncWorker
import com.github.educationissimple.tasks.presentation.screens.CalendarScreen
import com.github.educationissimple.tasks.presentation.screens.TaskCategoriesScreen
import com.github.educationissimple.tasks.presentation.screens.TaskRemindersScreen
import com.github.educationissimple.tasks.presentation.screens.TasksScreen
import kotlinx.coroutines.delay
import java.time.Duration
import java.util.Calendar
import java.util.concurrent.TimeUnit

/**
 * A composable function that sets up the navigation system for the app and handles the primary UI layout,
 * including the top bar, bottom navigation bar, and navigation between different screens.
 * It also manages the periodic sync for reminders and handles app exit behavior.
 *
 * This function initializes necessary services such as periodic work requests and manages navigation for three
 * primary sections: Tasks, Audio, and News. It includes dynamic top bar actions based on the current screen and
 * provides a mechanism to confirm app exit with a double-press on the back button.
 *
 * @param onStartAudioService A lambda function to start the audio service when required.
 * @param onStopAudioService A lambda function to stop the audio service when required.
 */
@Composable
fun AppNavigation(
    onStartAudioService: () -> Unit,
    onStopAudioService: () -> Unit
) {
    val context = LocalContext.current.applicationContext

    // Initialize periodic work request for reminders sync after midnight.
    LaunchedEffect(Unit) {
        val currentTime = Calendar.getInstance()
        val midnight = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            add(Calendar.DAY_OF_YEAR, 1)
        }
        val timeUntilMidnight = midnight.timeInMillis - currentTime.timeInMillis

        val workRequest = PeriodicWorkRequestBuilder<RemindersSyncWorker>(
            repeatInterval = 1,
            repeatIntervalTimeUnit = TimeUnit.DAYS
        ).setBackoffCriteria(
            backoffPolicy = BackoffPolicy.LINEAR,
            duration = Duration.ofMinutes(5)
        ).addTag(RemindersSyncWorker.TAG)
            .setInitialDelay(timeUntilMidnight, TimeUnit.MILLISECONDS).build()
        val workManager = WorkManager.getInstance(context)
        workManager.enqueueUniquePeriodicWork(
            RemindersSyncWorker.TAG,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    val navController = rememberNavController()
    val currentBackStackEntry = navController.currentBackStackEntryAsState()

    // Determine the current screen's title based on the route of the current navigation entry.
    val titleRes: Int? = when (currentBackStackEntry.value.routeClass()) {
        TasksGraph.TaskCategoriesScreen::class -> R.string.manage_task_categories
        TasksGraph.TasksScreen::class -> R.string.tasks
        TasksGraph.CalendarScreen::class -> R.string.calendar
        TasksGraph.RemindersScreen::class -> R.string.reminders
        AudioGraph.AudioScreen::class -> R.string.audio
        AudioGraph.AudioCategoriesScreen::class -> R.string.manage_audio_categories
        NewsGraph.NewsScreen::class -> R.string.science_news
        else -> null
    }

    // Left icon action: handle navigation back if there is a previous screen.
    val leftIconAction: IconAction? = if (navController.previousBackStackEntry == null) {
        null
    } else {
        IconAction(Icons.AutoMirrored.Filled.KeyboardArrowLeft) { navController.popBackStack() }
    }

    // Manage state for search enablement in the tasks screen.
    var tasksScreenSearchEnabled by remember { mutableStateOf(false) }


    var exit by remember { mutableStateOf(false) }

    // Handle exit logic with a 2-second delay on back button press.
    LaunchedEffect(key1 = exit) {
        if (exit) {
            delay(2000)
            exit = false
        }
    }

    // Back button press handler.
    BackHandler {
        if (exit) {
            context.startActivity(Intent(Intent.ACTION_MAIN).apply {
                addCategory(Intent.CATEGORY_HOME)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            })
        } else {
            exit = true
            Core.toaster.showToast("Нажмите еще раз чтобы выйти")
        }
    }

    // Define right icon actions based on the current screen.
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

    // Scaffold the layout with the top bar, bottom bar, and navigation host.
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
                    TasksScreen(
                        searchEnabled = tasksScreenSearchEnabled,
                        onSearchEnabledChange = { tasksScreenSearchEnabled = it }
                    )
                }
                composable<TasksGraph.TaskCategoriesScreen> {
                    TaskCategoriesScreen()
                }
                composable<TasksGraph.CalendarScreen> {
                    CalendarScreen()
                }
                composable<TasksGraph.RemindersScreen> {
                    TaskRemindersScreen()
                }
            }

            navigation<AudioGraph>(
                startDestination = AudioGraph.AudioScreen
            ) {
                composable<AudioGraph.AudioScreen> {
                    AudioListScreen(onStartAudioService = onStartAudioService)
                }
                composable<AudioGraph.AudioCategoriesScreen> {
                    AudioCategoriesScreen()
                }
            }

            navigation<NewsGraph>(
                startDestination = NewsGraph.NewsScreen
            ) {
                composable<NewsGraph.NewsScreen> {
                    NewsScreen()
                }
            }
        }
    }
}