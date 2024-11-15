package com.github.educationissimple.news.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.github.educationissimple.components.colors.Highlight
import com.github.educationissimple.components.colors.Neutral
import com.github.educationissimple.news.domain.entities.NewsEntity
import com.github.educationissimple.presentation.locals.LocalSpacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsContentSheet(
    news: NewsEntity,
    onMoreClick: () -> Unit,
    isSheetOpen: Boolean,
    onDismiss: () -> Unit
) {

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(isSheetOpen) {
        if (isSheetOpen) {
            sheetState.expand()
        } else {
            sheetState.hide()
        }
    }

    ModalBottomSheet(sheetState = sheetState, onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (news.imageUrl != null) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(news.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = LocalSpacing.current.medium),
                horizontalAlignment = Alignment.Start,
            ) {
                Spacer(modifier = Modifier.height(LocalSpacing.current.extraSmall))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.weight(0.5f)
                    ) {
                        Text(
                            text = news.author,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(modifier = Modifier.width(LocalSpacing.current.extraSmall))
                    Row(
                        modifier = Modifier.weight(0.5f),
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(
                            onClick = onMoreClick,
                            colors = IconButtonDefaults.iconButtonColors(contentColor = Highlight.Darkest)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.OpenInNew,
                                contentDescription = null
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(LocalSpacing.current.extraSmall))

                Text(
                    text = news.publishedAt,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Neutral.Dark.Light
                )
                Spacer(modifier = Modifier.height(LocalSpacing.current.medium))
                Text(
                    text = news.title,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(LocalSpacing.current.medium))
                Text(text = news.content)

            }
        }
    }
}