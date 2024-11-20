package com.github.educationissimple.audio.presentation.components.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.github.educationissimple.audio.R
import com.github.educationissimple.audio.domain.entities.Audio
import com.github.educationissimple.audio.presentation.components.PlayingIndicator
import com.github.educationissimple.audio.presentation.entities.dummies.dummyAudio
import com.github.educationissimple.components.composables.shimmerEffect
import com.github.educationissimple.presentation.locals.LocalSpacing

/**
 * Composable that displays an item in the audio list, including the audio's image, title, subtitle,
 * and an optional "delete" menu for audio removal.
 *
 * The item highlights when selected and shows a playing indicator if the audio is currently playing.
 *
 * @param audio The audio object containing the information to be displayed (e.g., title, subtitle, image).
 * @param onClick A callback function to be invoked when the audio list item is clicked.
 * @param onAudioDelete A callback function to be invoked when the delete option is selected.
 * @param isAudioPlaying A boolean value indicating whether the audio is currently playing.
 * @param isSelected A boolean value indicating whether the item is selected.
 * @param modifier A modifier to be applied to the audio list item.
 */
@Composable
fun AudioListItem(
    audio: Audio,
    onClick: () -> Unit,
    onAudioDelete: () -> Unit,
    isAudioPlaying: Boolean,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    var showMenu by remember { mutableStateOf(false) }

    Surface(
        color = if (isSelected) MaterialTheme.colorScheme.surfaceContainerLow else MaterialTheme.colorScheme.surface,
        modifier = Modifier.height(IntrinsicSize.Min),
        shape = MaterialTheme.shapes.extraSmall,
        onClick = onClick
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(LocalSpacing.current.small),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                Modifier
                    .size(40.dp)
                    .clip(MaterialTheme.shapes.extraSmall),
                contentAlignment = Alignment.Center
            ) {
                if (audio.imageBitmap == null) {
                    Image(
                        imageVector = Icons.Default.MusicNote,
                        contentDescription = null,
                    )
                } else {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(audio.imageBitmap)
                            .crossfade(true)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
                if (isSelected) {
                    PlayingIndicator(isPlaying = isAudioPlaying, modifier = Modifier.size(10.dp))
                }
            }

            Spacer(modifier = Modifier.width(LocalSpacing.current.small))

            Column {
                Text(text = audio.title)
                Text(text = audio.subtitle, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }

            Spacer(modifier = Modifier.weight(1f))

            Box(Modifier.width(24.dp)) {
                IconButton(
                    onClick = { showMenu = true },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = stringResource(R.string.audio_menu)
                    )
                }
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false },
                ) {
                    DropdownMenuItem(
                        text = { Text(text = stringResource(R.string.delete)) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null
                            )
                        },
                        onClick = onAudioDelete
                    )
                }
            }
        }
    }
}

@Composable
fun LoadingAudioListItem() {
    Surface(
        shape = MaterialTheme.shapes.extraSmall
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shimmerEffect()
                .padding(LocalSpacing.current.small),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                Modifier.size(40.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(MaterialTheme.shapes.extraSmall)
                        .background(color = MaterialTheme.colorScheme.onSurfaceVariant)
                )
            }

            Spacer(modifier = Modifier.width(LocalSpacing.current.small))

            Column(
                verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.extraSmall)
            ) {
                Box(
                    modifier = Modifier
                        .clip(shape = MaterialTheme.shapes.extraSmall)
                        .size(height = 12.dp, width = (40..80).random().dp)
                        .background(color = MaterialTheme.colorScheme.onSurfaceVariant)
                )
                Box(
                    modifier = Modifier
                        .clip(shape = MaterialTheme.shapes.extraSmall)
                        .size(height = 10.dp, width = (32..72).random().dp)
                        .background(color = MaterialTheme.colorScheme.onSurfaceVariant)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = { },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = stringResource(R.string.audio_menu),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}


@Preview
@Composable
fun AudioListItemPreview() {
    Box(modifier = Modifier.width(300.dp)) {
        AudioListItem(
            audio = dummyAudio,
            onClick = {},
            onAudioDelete = {},
            isAudioPlaying = false,
            isSelected = true
        )
    }
}

@Preview
@Composable
fun LoadingAudioListItemPreview() {
    Box(modifier = Modifier.width(300.dp)) {
        LoadingAudioListItem()
    }
}