package com.github.educationissimple.tasks.presentation.components.lists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ContextualFlowRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.components.composables.buttons.DefaultIconButton
import com.github.educationissimple.components.composables.items.LoadingActionableListItem
import com.github.educationissimple.presentation.ResultContainerComposable
import com.github.educationissimple.presentation.locals.LocalSpacing
import com.github.educationissimple.tasks.domain.entities.TaskReminder
import com.github.educationissimple.tasks.presentation.components.items.ReminderListItem

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RemindersList(
    reminders: ResultContainer<List<TaskReminder>>,
    onDelete: (TaskReminder) -> Unit,
    onCreate: (TaskReminder) -> Unit,
    modifier: Modifier = Modifier
) {
    var showAddReminderDialog by remember { mutableStateOf(false) }

    ResultContainerComposable(
        container = reminders,
        onTryAgain = {},
        onLoading = {
            repeat(3) {
                LoadingActionableListItem()
            }
        }
    ) {
        ContextualFlowRow(
            itemCount = reminders.unwrap().size + 1,
            horizontalArrangement = Arrangement.spacedBy(LocalSpacing.current.medium),
            verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.medium),
            modifier = modifier
        ) { index ->
            if (index == reminders.unwrap().size + 1) {
                DefaultIconButton(
                    onClick = { showAddReminderDialog = true },
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                }
            } else {
                val reminder = reminders.unwrap()[index]
                key(reminder.id) {
                    ReminderListItem(
                        reminder = reminder,
                        actionIconVector = Icons.Default.Delete,
                        onAction = { onDelete(reminder) }
                    )
                }
            }
        }
    }

}