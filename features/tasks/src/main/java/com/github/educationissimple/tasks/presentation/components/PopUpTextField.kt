package com.github.educationissimple.tasks.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.educationissimple.common.Core
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.components.colors.Neutral
import com.github.educationissimple.components.composables.DefaultIconButton
import com.github.educationissimple.components.composables.DefaultTextField
import com.github.educationissimple.components.composables.ScreenDimming
import com.github.educationissimple.tasks.R
import com.github.educationissimple.tasks.domain.entities.TaskCategory
import com.github.educationissimple.tasks.domain.entities.TaskCategory.Companion.NO_CATEGORY_ID

@Composable
fun PopUpTextField(
    text: String,
    onValueChange: (String) -> Unit,
    onAddClick: (Long) -> Unit,
    onAddNewCategory: (String) -> Unit,
    categories: ResultContainer<List<TaskCategory>>,
    focusRequester: FocusRequester,
    modifier: Modifier = Modifier,
) = Box(modifier = Modifier.fillMaxSize()) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Neutral.Light.Lightest),
        shape = RoundedCornerShape(
            topStart = 12.dp,
            topEnd = 12.dp,
            bottomEnd = 0.dp,
            bottomStart = 0.dp
        ),
        modifier = modifier.align(Alignment.BottomCenter)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp, top = 12.dp, bottom = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            DefaultTextField(
                text = text,
                onValueChange = onValueChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding()
                    .focusRequester(focusRequester)
            )
            Row {
                var selectedCategory by remember {
                    mutableStateOf(
                        TaskCategory(
                            NO_CATEGORY_ID,
                            Core.resources.getString(R.string.no_category)
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
                TaskCategoryCard(
                    category = selectedCategory,
                    onCategoryClick = { showCategoriesDialog = true },
                    modifier = Modifier.sizeIn(maxWidth = 150.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                DefaultIconButton(
                    onClick = { onAddClick(selectedCategory.id) },
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
}

@Preview(showSystemUi = true)
@Composable
fun PopUpTextFieldPreview() {

    ScreenDimming { }

    PopUpTextField(
        text = "text",
        onValueChange = {},
        onAddClick = {},
        onAddNewCategory = {},
        focusRequester = FocusRequester(),
        categories = ResultContainer.Done(listOf())
    )
}