package com.github.educationissimple.tasks.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.components.composables.ActionableListItem
import com.github.educationissimple.components.composables.AddFloatingActionButton
import com.github.educationissimple.components.composables.LoadingActionableListItem
import com.github.educationissimple.presentation.ResultContainerComposable
import com.github.educationissimple.presentation.locals.LocalSpacing
import com.github.educationissimple.tasks.di.TasksDiContainer
import com.github.educationissimple.tasks.di.rememberTasksDiContainer
import com.github.educationissimple.tasks.domain.entities.TaskCategory
import com.github.educationissimple.tasks.presentation.components.dialogs.AddCategoryDialog
import com.github.educationissimple.tasks.presentation.events.TasksEvent
import com.github.educationissimple.tasks.presentation.viewmodels.TasksViewModel

@Composable
fun CategoriesScreen(
    diContainer: TasksDiContainer = rememberTasksDiContainer(),
    viewModel: TasksViewModel = viewModel(factory = diContainer.viewModelFactory)
) {
    CategoriesContent(
        categories = viewModel.categories.collectAsStateWithLifecycle().value,
        onEvent = viewModel::onEvent
    )
}


@Composable
fun CategoriesContent(
    categories: ResultContainer<List<TaskCategory>>,
    onEvent: (TasksEvent) -> Unit
) {
    var isAddingNewCategory by remember { mutableStateOf(false) }

    ResultContainerComposable(
        container = categories,
        onTryAgain = { },
        onLoading = {
            Column(
                verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.semiMedium),
                modifier = Modifier.padding(20.dp)
            ) {
                repeat(5) {
                    LoadingActionableListItem(withDelete = true)
                }
            }
        }
    ) {

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.semiMedium),
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(items = categories.unwrap(), key = { category -> category.id }) { category ->
                ActionableListItem(
                    label = category.name,
                    onDelete = { onEvent(TasksEvent.DeleteCategory(category.id)) },
                    modifier = Modifier.animateItem()
                )
            }
        }

        if (isAddingNewCategory) {
            AddCategoryDialog(
                onConfirm = { categoryName ->
                    onEvent(TasksEvent.AddCategory(categoryName))
                    isAddingNewCategory = false
                },
                onCancel = { isAddingNewCategory = false }
            )
        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                AddFloatingActionButton(
                    onClick = { isAddingNewCategory = true },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(LocalSpacing.current.medium)
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun CategoriesContentPreview() {
    val categories = (1..5).map {
        TaskCategory(it.toLong(), "Category $it")
    }
    CategoriesContent(ResultContainer.Done(categories), onEvent = { })
}

@Preview(showSystemUi = true)
@Composable
fun CategoriesContentLoadingPreview() {
    CategoriesContent(ResultContainer.Loading, onEvent = { })
}