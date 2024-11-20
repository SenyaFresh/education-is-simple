package com.github.educationissimple.tasks.presentation.components.lists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.components.composables.buttons.DefaultIconButton
import com.github.educationissimple.components.composables.items.LoadingActionableListItem
import com.github.educationissimple.presentation.ResultContainerComposable
import com.github.educationissimple.presentation.locals.LocalSpacing
import com.github.educationissimple.tasks.domain.entities.TaskReminder
import com.github.educationissimple.tasks.presentation.components.items.ReminderListItem

/**
 * Displays a list of task reminders with the option to delete individual reminders and create new ones.
 * The list is dynamically updated based on the [reminders] data, and the reminders can be interacted with through the provided actions.
 * The user can click on an icon to delete a reminder or press a button to create a new one.
 *
 * This composable function manages the display of task reminders and includes functionality for deleting and creating reminders.
 * It shows a loading state while the reminders are being fetched and provides an option to reload the reminders if needed.
 * The reminders are displayed in a vertical list with a delete action for each item.
 *
 * - The [reminders] container holds the list of [TaskReminder] objects to be displayed.
 * - Each reminder is displayed with a delete action icon that, when clicked, triggers the [onDelete] callback.
 * - A "create" button is provided to allow the user to add new reminders, triggering the [onCreate] callback when clicked.
 * - The list is wrapped in a [LazyColumn] for efficient rendering of potentially large lists.
 * - The loading state is managed with a loading indicators while the reminders are being fetched.
 * - A reload option is provided through the [onReloadReminders] callback, allowing the user to refresh the list of reminders.
 *
 * @param reminders A [ResultContainer] containing the list of [TaskReminder] objects to be displayed in the list.
 * @param onDelete A callback function invoked when a reminder is deleted. It receives the [TaskReminder] to be deleted.
 * @param onCreate A callback function invoked when the user clicks the "create" button to add a new reminder.
 * @param onReloadReminders A callback function invoked to reload the list of reminders.
 * @param modifier A [Modifier] to customize the appearance and layout of the list.
 */
@Composable
fun RemindersList(
    reminders: ResultContainer<List<TaskReminder>>,
    onDelete: (TaskReminder) -> Unit,
    onCreate: () -> Unit,
    onReloadReminders: () -> Unit,
    modifier: Modifier = Modifier
) {
    ResultContainerComposable(
        container = reminders,
        onTryAgain = onReloadReminders,
        onLoading = {
            Column(
                verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.medium),
            ) {
                repeat(3) {
                    LoadingActionableListItem()
                }
            }
        }
    ) {
        Row {
            LazyColumn(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.medium),
                modifier = modifier.padding(LocalSpacing.current.small)
            ) {
                items(items = reminders.unwrap(), key = { it.id }) { reminder ->
                    ReminderListItem(
                        reminder = reminder,
                        actionIconVector = Icons.Default.Delete,
                        onAction = { onDelete(reminder) }
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            DefaultIconButton(
                onClick = onCreate,
                modifier = Modifier.padding(LocalSpacing.current.small),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.onTertiary,
                    contentColor =MaterialTheme.colorScheme.tertiary
                )
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    }

}