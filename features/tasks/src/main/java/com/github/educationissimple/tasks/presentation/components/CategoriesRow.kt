package com.github.educationissimple.tasks.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.presentation.ResultContainerComposable
import com.github.educationissimple.tasks.domain.entities.TaskCategory

@Composable
fun CategoriesRow(
    categories: ResultContainer<List<TaskCategory>>,
    onCategoryClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    activeCategory: Long = 0
) {
    ResultContainerComposable(container = categories, onTryAgain = { }) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier
        ) {
            val categoriesList = listOf(TaskCategory(id = 0, name = "Все")) + categories.unwrap()

            items(
                items = categoriesList,
                key = { category -> category.id to (category.id == activeCategory) }) { category ->
                TaskCategoryCard(
                    category = category,
                    isActive = category.id == activeCategory,
                    onCategoryClick = onCategoryClick
                )
            }
        }
    }
}