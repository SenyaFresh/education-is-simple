package com.github.educationissimple.audio.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.educationissimple.audio.R
import com.github.educationissimple.audio.di.AudioDiContainer
import com.github.educationissimple.audio.di.rememberAudioDiContainer
import com.github.educationissimple.audio.domain.entities.AudioCategory
import com.github.educationissimple.audio.presentation.events.AudioEvent
import com.github.educationissimple.audio.presentation.viewmodels.AudioViewModel
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.components.composables.lists.ActionableItemsListWithAdding
import com.github.educationissimple.components.entities.ActionableItem

@Composable
fun AudioCategoriesScreen(
    diContainer: AudioDiContainer = rememberAudioDiContainer(),
    viewModel: AudioViewModel = viewModel(factory = diContainer.viewModelFactory.create())
) {
    CategoriesContent(
        categories = viewModel.audioCategories.collectAsStateWithLifecycle().value,
        onEvent = viewModel::onEvent
    )
}


@Composable
fun CategoriesContent(
    categories: ResultContainer<List<AudioCategory>>,
    onEvent: (AudioEvent) -> Unit
) {
    ActionableItemsListWithAdding(
        items = categories.map { list -> list.map { ActionableItem(it.id, it.name) } },
        addLabel = stringResource(R.string.add_new_category),
        addPlaceholder = stringResource(R.string.input_new_category_here),
        emptyListMessage = stringResource(R.string.no_categories_yet),
        onDelete = { categoryId ->
            onEvent(AudioEvent.DeleteCategoryEvent(categoryId))
        },
        onAdd = { categoryName ->
            onEvent(AudioEvent.CreateCategoryEvent(categoryName))
        },
        onReloadItems = {
            onEvent(AudioEvent.ReloadAudioCategoriesEvent)
        }
    )
}

@Preview(showSystemUi = true)
@Composable
fun CategoriesContentPreview() {
    val categories = (1..5).map {
        AudioCategory(it.toLong(), "Category $it")
    }
    CategoriesContent(ResultContainer.Done(categories), onEvent = { })
}

@Preview(showSystemUi = true)
@Composable
fun CategoriesContentLoadingPreview() {
    CategoriesContent(ResultContainer.Loading, onEvent = { })
}