package com.github.educationissimple.tasks.presentation.components.items

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.educationissimple.components.composables.items.ActionableListItem
import com.github.educationissimple.presentation.locals.LocalSpacing
import com.github.educationissimple.tasks.domain.entities.TaskReminder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Displays a list item for a task reminder with an action icon.
 *
 * This composable function creates a list item for displaying a task reminder. The reminder's date and time are formatted and shown as the label.
 * The item includes a clickable area where the user can toggle the active state of the reminder. When active, an action icon is shown,
 * which triggers the provided [onAction] callback when clicked.
 *
 * - The reminder's date and time is formatted using the pattern "dd.MM.yyyy HH:mm".
 * - The reminder can be marked as active or inactive by clicking on the list item.
 * - When the reminder is active, an action icon is displayed to allow the user to perform an action, such as deleting or editing the reminder.
 *
 * @param reminder A [TaskReminder] object representing the task reminder. The date and time are used for displaying the reminder.
 * @param actionIconVector An [ImageVector] representing the icon to be displayed when the reminder is active. It can be any icon, such as a delete or edit icon.
 * @param onAction A callback function invoked when the action icon is clicked. The function should define the action to be taken, like deleting or editing the reminder.
 */
@Composable
fun ReminderListItem(
    reminder: TaskReminder,
    actionIconVector: ImageVector,
    onAction: () -> Unit,
) {
    var isActive by remember { mutableStateOf(false) }
    Card(
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onTertiary),
        modifier = Modifier.animateContentSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = LocalSpacing.current.small)
        ) {
            ActionableListItem(
                label = reminder.datetime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")),
                onClick = {
                    isActive = !isActive
                },
                isActive = isActive,
            )
            Spacer(modifier = Modifier.width(LocalSpacing.current.small))
            if (isActive) {
                IconButton(
                    onClick = onAction
                ) {
                    Icon(
                        imageVector = actionIconVector,
                        contentDescription = null,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun RemindersListItemPreview() {
    ReminderListItem(
        reminder = TaskReminder(
            id = 1,
            taskId = 1,
            taskText = "",
            datetime = LocalDateTime.now(),
        ),
        actionIconVector = Icons.Default.Delete,
        onAction = {}
    )
}