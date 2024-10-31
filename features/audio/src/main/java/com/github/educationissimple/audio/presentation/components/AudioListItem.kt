package com.github.educationissimple.audio.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.educationissimple.audio.R
import com.github.educationissimple.audio.domain.entities.Audio
import com.github.educationissimple.components.colors.Neutral
import com.github.educationissimple.presentation.locals.LocalSpacing

@Composable
fun AudioListItem(
    audio: Audio,
    onClick: () -> Unit,
    onMenuClick: () -> Unit,
    isAudioPlaying: Boolean,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        color = if (isSelected) Neutral.Light.Medium else Neutral.Light.Lightest,
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
                Modifier.size(40.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painterResource(audio.imageRes),
                    contentDescription = stringResource(R.string.audio_image),
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(4.dp))
                )
                if (isSelected) {
                    PlayingIndicator(isPlaying = isAudioPlaying, modifier = Modifier.size(10.dp))
                }
            }

            Spacer(modifier = Modifier.width(LocalSpacing.current.small))

            Column {
                Text(text = audio.title)
                Text(text = audio.subtitle, fontSize = 10.sp, color = Neutral.Dark.Light)
            }

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = onMenuClick,
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = stringResource(R.string.audio_menu)
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
            Audio(
                id = 0,
                imageRes = R.drawable.audio_image_preview,
                title = "Audio Title",
                subtitle = "Audio Subtitle",
                duration = 0
            ),
            onClick = {},
            onMenuClick = {},
            isAudioPlaying = false,
            isSelected = true
        )
    }
}