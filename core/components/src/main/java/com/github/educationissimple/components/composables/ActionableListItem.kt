package com.github.educationissimple.components.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.educationissimple.components.R
import com.github.educationissimple.components.colors.Highlight
import com.github.educationissimple.components.colors.Neutral
import com.github.educationissimple.components.colors.Support

@Composable
fun ActionableListItem(
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { },
    onDelete: (() -> Unit)? = null,
    isActive: Boolean = false,
) {
    val textColor by remember(isActive) {
        derivedStateOf {
            if (isActive) Neutral.Light.Lightest else Highlight.Darkest
        }
    }

    val backgroundColor by remember(isActive) {
        derivedStateOf {
            if (isActive) Highlight.Darkest else Highlight.Lightest
        }
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(18.dp),
        onClick = onClick,
        modifier = modifier,
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label.uppercase(),
                color = textColor,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                )
            )

            onDelete?.let {
                Spacer(modifier = Modifier.weight(1f))
                ActionIcon(
                    imageVector = Icons.Default.Delete,
                    text = stringResource(R.string.delete),
                    contentColor = Neutral.Light.Lightest,
                    containerColor = Support.Error.Dark,
                    modifier = Modifier.height(60.dp),
                    onClick = onDelete
                )
            }
        }
    }
}

@Composable
fun LoadingActionableListItem(withDelete: Boolean = false) {
    val modifier = if (withDelete) Modifier.fillMaxWidth() else Modifier
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(18.dp))
            .height(
                if (withDelete) 50.dp else 30.dp
            )
            .widthIn(min = 72.dp)
            .shimmerEffect()
    ) {
        if (withDelete) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(color = Neutral.Dark.Light)
                    .align(Alignment.CenterEnd)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ActionableListItemPreview() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(20.dp)
    ) {
        (1..2).forEach {
            ActionableListItem(
                label = "Category $it",
                isActive = it % 2 == 0,
                onClick = { }
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun LoadingActionableListItemPreview() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(20.dp)
    ) {
        repeat(2) {
            LoadingActionableListItem()
        }

    }
}

@Preview(showSystemUi = true)
@Composable
fun ActionableListItemWithDeletePreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(20.dp)
    ) {
        (1..3).forEach {
            ActionableListItem(
                label = "Category $it",
                onDelete = { },
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun LoadingActionableListItemWithDeletePreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(20.dp)
    ) {
        repeat(3) {
            LoadingActionableListItem(withDelete = true)
        }
    }
}