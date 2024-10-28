package com.github.educationissimple.tasks.presentation.components.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.educationissimple.components.colors.Highlight
import com.github.educationissimple.components.colors.Neutral
import com.github.educationissimple.presentation.locals.LocalSpacing
import com.github.educationissimple.presentation.shimmerEffect
import com.github.educationissimple.tasks.domain.entities.TaskCategory

@Composable
fun TaskCategoryListItem(
    category: TaskCategory,
    onCategoryClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    isActive: Boolean = false,
) {
    val textColor by remember(isActive) {
        derivedStateOf {
            if (isActive) Neutral.Light.Lightest else Highlight.Darkest
        }
    }

    val backgroundColor by remember {
        derivedStateOf {
            if (isActive) Highlight.Darkest else Highlight.Lightest
        }
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(18.dp),
        onClick = { onCategoryClick(category.id) },
        modifier = modifier,
    ) {
        Text(
            text = category.name.uppercase(),
            color = textColor,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(
                horizontal = LocalSpacing.current.medium,
                vertical = LocalSpacing.current.small
            )
        )
    }
}

@Composable
fun LoadingTaskCategoryListItem() {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(18.dp))
            .size(height = 30.dp, width = 72.dp)
            .shimmerEffect()
    )
}

@Preview(showSystemUi = true)
@Composable
fun TaskCategoryCardPreview() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(20.dp)
    ) {
        (1..2).forEach {
            TaskCategoryListItem(
                category = TaskCategory(it.toLong(), "Category $it"),
                isActive = it % 2 == 0,
                onCategoryClick = { }
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun LoadingTaskCategoryCardPreview() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(20.dp)
    ) {
        repeat(2) {
            LoadingTaskCategoryListItem()
        }

    }
}