package com.github.educationissimple.news.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.github.educationissimple.components.composables.shimmerEffect
import com.github.educationissimple.news.R
import com.github.educationissimple.news.domain.entities.NewsEntity
import com.github.educationissimple.presentation.locals.LocalSpacing

/**
 * A composable function to display a news item as a list element, with a clickable layout
 * that opens a detailed bottom sheet for the news content. Handles image loading states
 * and provides an option to view more details via a callback.
 *
 * @param news The [NewsEntity] containing the news details to display.
 * @param onMoreClick A callback triggered when the "More" text is clicked, typically to open the full article in a browser.
 */
@Composable
fun NewsListItem(
    news: NewsEntity,
    onMoreClick: () -> Unit
) {
    var showNewsContent by remember { mutableStateOf(false) }
    var imageState by remember { mutableStateOf(ImageLoadingState.Loading) }

    if (showNewsContent) {
        NewsContentSheet(
            news = news,
            onMoreClick = onMoreClick,
            isSheetOpen = showNewsContent,
            onDismiss = { showNewsContent = false }
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(LocalSpacing.current.medium)
            .clickable { showNewsContent = true },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            if (news.imageUrl == null || imageState == ImageLoadingState.Error) {
                Icon(
                    imageVector = Icons.Default.Newspaper,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.inverseOnSurface,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.inverseSurface)
                        .padding(LocalSpacing.current.medium)
                )
            } else {
                if (imageState == ImageLoadingState.Loading) {
                    Spacer(
                        modifier = Modifier
                            .fillMaxSize()
                            .shimmerEffect()
                    )
                }
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(news.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    onLoading = {
                        imageState = ImageLoadingState.Loading
                    },
                    onSuccess = {
                        imageState = ImageLoadingState.Success
                    },
                    onError = {
                        imageState = ImageLoadingState.Error
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        Spacer(modifier = Modifier.width(LocalSpacing.current.medium))
        Column {
            Text(
                text = news.title,
                fontSize = 16.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            HorizontalDivider(
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = LocalSpacing.current.extraSmall)
            )
            Text(
                text = news.source,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.weight(0.5f)
            )
            Row(
                modifier = Modifier.weight(0.5f)
            ) {
                Text(
                    text = news.publishedAt,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.weight(0.5f)
                )
                Spacer(modifier = Modifier.width(LocalSpacing.current.extraSmall))
                Row(
                    modifier = Modifier.weight(0.5f)
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = stringResource(R.string.more),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.clickable { onMoreClick() }
                    )
                }
            }
        }
    }
}

enum class ImageLoadingState {
    Loading,
    Success,
    Error
}

@Preview(showSystemUi = true)
@Composable
fun NewsListItemPreview() {
    NewsListItem(
        news = NewsEntity(
            title = "Title title title title title title title title title title title title title title title title title title title title",
            source = "Author",
            publishedAt = "2 hours ago",
            imageUrl = null,
            content = "",
            url = ""
        ),
        onMoreClick = {}
    )
}