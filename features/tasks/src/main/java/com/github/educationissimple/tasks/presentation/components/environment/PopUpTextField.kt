package com.github.educationissimple.tasks.presentation.components.environment

import android.view.Gravity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.components.colors.Neutral
import com.github.educationissimple.components.composables.DefaultIconButton
import com.github.educationissimple.components.composables.DefaultTextField
import com.github.educationissimple.presentation.locals.LocalSpacing
import com.github.educationissimple.tasks.R
import com.github.educationissimple.tasks.domain.entities.TaskCategory
import com.github.educationissimple.tasks.domain.entities.TaskCategory.Companion.NO_CATEGORY_ID
import com.github.educationissimple.tasks.presentation.components.dialogs.SelectCategoryDialog
import com.github.educationissimple.tasks.presentation.components.items.ActionableListItem

@Composable
fun PopUpTextField(
    text: String,
    onValueChange: (String) -> Unit,
    onAddClick: (Long) -> Unit,
    onAddNewCategory: (String) -> Unit,
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
        colors = CardDefaults.cardColors(containerColor = Neutral.Light.Lightest),
        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        modifier = modifier
    ) {
        PopUpTextFieldContent(
            text = text,
            onValueChange = onValueChange,
            onAddClick = onAddClick,
            onAddNewCategory = onAddNewCategory,
            categories = categories,
            focusRequester = focusRequester
        )
    }
}

@Composable
fun PopUpTextFieldContent(
    text: String,
    onValueChange: (String) -> Unit,
    onAddClick: (Long) -> Unit,
    onAddNewCategory: (String) -> Unit,
    categories: ResultContainer<List<TaskCategory>>,
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
    var showCategoriesDialog by remember { mutableStateOf(false) }

    if (showCategoriesDialog) {
        SelectCategoryDialog(
            categories = categories,
            onConfirm = {
                showCategoriesDialog = false
                selectedCategory = it
            },
            onCancel = {
                showCategoriesDialog = false
            },
            onAddNewCategory = onAddNewCategory,
            initialActiveCategoryId = selectedCategory.id
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
            modifier = Modifier
                .fillMaxWidth()
                .imePadding()
                .focusRequester(focusRequester)
        )
        Row {
            // Category selection.
            ActionableListItem(
                label = selectedCategory.name,
                onClick = { showCategoriesDialog = true },
                modifier = Modifier.sizeIn(maxWidth = 150.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            // Add button.
            DefaultIconButton(
                onClick = { onAddClick(selectedCategory.id) },
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
        onAddClick = {},
        onAddNewCategory = {},
        focusRequester = FocusRequester(),
        onDismiss = { },
        categories = ResultContainer.Done(listOf())
    )
}