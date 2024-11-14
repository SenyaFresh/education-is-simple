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

@Composable
fun RemindersList(
    reminders: ResultContainer<List<TaskReminder>>,
    onDelete: (TaskReminder) -> Unit,
    onCreate: () -> Unit,
    modifier: Modifier = Modifier
) {
    ResultContainerComposable(
        container = reminders,
        onTryAgain = {},
        onLoading = {
            Column {
                repeat(3) {
                    LoadingActionableListItem()
                }
            }
        }
    ) {
        Row{
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
                modifier = Modifier.padding(LocalSpacing.current.small)
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    }

}