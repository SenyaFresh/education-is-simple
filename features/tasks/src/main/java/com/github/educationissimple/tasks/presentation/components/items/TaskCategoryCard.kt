package com.github.educationissimple.tasks.presentation.components.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.educationissimple.components.colors.Highlight
import com.github.educationissimple.components.colors.Neutral
import com.github.educationissimple.tasks.domain.entities.TaskCategory

@Composable
fun TaskCategoryCard(
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
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun TaskCategoryCardPreview() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(20.dp)
    ) {
        TaskCategoryCard(
            category = TaskCategory(0, "Work"),
            isActive = true,
            onCategoryClick = { }
        )
        TaskCategoryCard(
            category = TaskCategory(1, "Home"),
            isActive = false,
            onCategoryClick = { }
        )
    }
}