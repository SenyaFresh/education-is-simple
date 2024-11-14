package com.github.educationissimple.tasks.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.components.composables.lists.ActionableItemsListWithAdding
import com.github.educationissimple.components.entities.ActionableItem
import com.github.educationissimple.tasks.R
import com.github.educationissimple.tasks.di.TasksDiContainer
import com.github.educationissimple.tasks.di.rememberTasksDiContainer
import com.github.educationissimple.tasks.domain.entities.TaskCategory
import com.github.educationissimple.tasks.presentation.events.TasksEvent
import com.github.educationissimple.tasks.presentation.viewmodels.TasksViewModel

@Composable
fun TaskCategoriesScreen(
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
    ActionableItemsListWithAdding(
        items = categories.map { list -> list.map { ActionableItem(it.id, it.name) } },
        addLabel = stringResource(R.string.add_new_category),
        addPlaceholder = stringResource(R.string.input_new_category_here),
        emptyListMessage = stringResource(R.string.no_categories_yet),
        onDelete = { categoryId ->
            onEvent(TasksEvent.DeleteCategory(categoryId))
        },
        onAdd = { categoryName ->
            onEvent(TasksEvent.AddCategory(categoryName))
        }
    )
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