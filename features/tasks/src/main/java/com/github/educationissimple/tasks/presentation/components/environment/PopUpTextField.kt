package com.github.educationissimple.tasks.presentation.components.environment

import android.view.Gravity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.components.composables.buttons.DefaultIconButton
import com.github.educationissimple.components.composables.inputs.DefaultTextField
import com.github.educationissimple.components.composables.items.ActionableListItem
import com.github.educationissimple.presentation.locals.LocalSpacing
import com.github.educationissimple.tasks.R
import com.github.educationissimple.tasks.domain.entities.Task
import com.github.educationissimple.tasks.domain.entities.TaskCategory
import com.github.educationissimple.tasks.domain.entities.TaskCategory.Companion.NO_CATEGORY_ID
import com.github.educationissimple.tasks.domain.entities.TaskCategoryId
import com.github.educationissimple.tasks.presentation.components.dialogs.ChangeDateDialog
import com.github.educationissimple.tasks.presentation.components.dialogs.SelectCategoryDialog
import com.github.educationissimple.tasks.presentation.components.dialogs.TaskPriorityDialog
import com.github.educationissimple.tasks.presentation.utils.toColor
import java.time.LocalDate

/**
 * A composable dialog displaying a text field at the bottom of screen for inputting a task, with options to select category,
 * set a reminder date, and set priority. The dialog allows adding a task with the selected properties.
 *
 * @param text The current text in the task input field.
 * @param onValueChange A callback invoked when the task input text changes.
 * @param onAddClick A callback invoked when the user clicks the add button. It receives the selected category ID, date, and priority.
 * @param onAddNewCategory A callback to create a new task category.
 * @param onReloadCategories A callback to reload the task categories.
 * @param categories A container holding the list of available task categories.
 * @param focusRequester A [FocusRequester] to manage the focus of the task input field.
 * @param onDismiss A callback invoked when the dialog is dismissed.
 * @param modifier A [Modifier] to customize the appearance of the dialog.
 */
@Composable
fun PopUpTextField(
    text: String,
    onValueChange: (String) -> Unit,
    onAddClick: (TaskCategoryId, LocalDate, Task.Priority) -> Unit,
    onAddNewCategory: (String) -> Unit,
    onReloadCategories: () -> Unit,
    categories: ResultContainer<List<TaskCategory>>,
    focusRequester: FocusRequester,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) = Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false, dismissOnBackPress = true)
) {
    val dialogWindowProvider = LocalView.current.parent as DialogWindowProvider
    dialogWindowProvider.window.setGravity(Gravity.BOTTOM)

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        shape = MaterialTheme.shapes.medium.copy(bottomStart = CornerSize(0), bottomEnd = CornerSize(0)),
        modifier = modifier
    ) {
        PopUpTextFieldContent(
            text = text,
            onValueChange = onValueChange,
            onAddClick = onAddClick,
            onAddNewCategory = onAddNewCategory,
            categories = categories,
            onReloadCategories = onReloadCategories,
            focusRequester = focusRequester
        )
    }
}

@Composable
fun PopUpTextFieldContent(
    text: String,
    onValueChange: (String) -> Unit,
    onAddClick: (TaskCategoryId, LocalDate, Task.Priority) -> Unit,
    onAddNewCategory: (String) -> Unit,
    categories: ResultContainer<List<TaskCategory>>,
    onReloadCategories: () -> Unit,
    focusRequester: FocusRequester
) {
    val noCategoryText = stringResource(R.string.no_category)
    var selectedCategory by remember {
        mutableStateOf(
            TaskCategory(
                NO_CATEGORY_ID,
                noCategoryText
            )
        )
    }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedPriority: Task.Priority by remember { mutableStateOf(Task.Priority.NoPriority) }

    var showCategoriesDialog by remember { mutableStateOf(false) }
    var showDateDialog by remember { mutableStateOf(false) }
    var showPriorityDialog by remember { mutableStateOf(false) }

    if (showCategoriesDialog) {
        SelectCategoryDialog(
            title = stringResource(R.string.select_task_category),
            categories = categories,
            onConfirm = {
                showCategoriesDialog = false
                selectedCategory = it
            },
            onCancel = {
                showCategoriesDialog = false
            },
            onAddNewCategory = onAddNewCategory,
            onReloadCategories = onReloadCategories,
            initialActiveCategoryId = selectedCategory.id
        )
    }

    if (showDateDialog) {
        ChangeDateDialog(
            onConfirm = {
                selectedDate = it
                showDateDialog = false
            },
            onDismiss = {
                showDateDialog = false
            }
        )
    }

    if (showPriorityDialog) {
        TaskPriorityDialog(
            priority = selectedPriority,
            onDismiss = { showPriorityDialog = false },
            onPriorityChange = {
                selectedPriority = it
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = LocalSpacing.current.semiMedium,
                end = LocalSpacing.current.semiMedium,
                top = LocalSpacing.current.semiMedium,
                bottom = LocalSpacing.current.extraSmall
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Task text field.
        DefaultTextField(
            text = text,
            onValueChange = onValueChange,
            placeholder = { Text(stringResource(R.string.input_task_here)) },
            modifier = Modifier
                .fillMaxWidth()
                .imePadding()
                .focusRequester(focusRequester)
        )
        Spacer(modifier = Modifier.padding(LocalSpacing.current.extraSmall))
        Row(
            horizontalArrangement = Arrangement.spacedBy(LocalSpacing.current.small)
        ) {
            // Category selection.
            ActionableListItem(
                label = selectedCategory.name,
                onClick = { showCategoriesDialog = true },
                modifier = Modifier.sizeIn(maxWidth = 150.dp)
            )

            DefaultIconButton(
                onClick = { showDateDialog = true }
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null
                )
            }

            DefaultIconButton(
                onClick = { showPriorityDialog = true },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = selectedPriority.toColor(),
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Add button.
            DefaultIconButton(
                onClick = { onAddClick(selectedCategory.id, selectedDate, selectedPriority) },
                enabled = text.isNotBlank()
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun PopUpTextFieldPreview() {
    PopUpTextField(
        text = "Задача",
        onValueChange = {},
        onAddClick = { _, _, _ -> },
        onAddNewCategory = {},
        focusRequester = FocusRequester(),
        onDismiss = { },
        onReloadCategories = {},
        categories = ResultContainer.Done(listOf())
    )
}