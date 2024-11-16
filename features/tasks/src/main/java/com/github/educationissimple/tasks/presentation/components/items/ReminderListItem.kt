package com.github.educationissimple.tasks.presentation.components.items

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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

@Composable
fun ReminderListItem(
    reminder: TaskReminder,
    actionIconVector: ImageVector,
    onAction: () -> Unit,
) {
    var isActive by remember { mutableStateOf(false) }
    Card(
        shape = RoundedCornerShape(18.dp),
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