package com.github.educationissimple.audio.presentation.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.educationissimple.audio.domain.entities.Audio
import com.github.educationissimple.audio.domain.entities.AudioCategory
import com.github.educationissimple.audio.domain.entities.AudioCategory.Companion.NO_CATEGORY_ID
import com.github.educationissimple.audio.domain.entities.PlayerController
import com.github.educationissimple.audio.presentation.components.lists.AudioCategoriesRow
import com.github.educationissimple.audio.presentation.components.lists.AudioItemsColumn
import com.github.educationissimple.audio.presentation.entities.dummies.dummyAudio
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.components.composables.AddFloatingActionButton
import com.github.educationissimple.presentation.locals.LocalSpacing

@Composable
fun AudioListContent(
    audioCategories: ResultContainer<List<AudioCategory>>,
    audioItems: ResultContainer<List<Audio>>,
    activeCategoryId: Long = NO_CATEGORY_ID,
    onPlaylistController: (PlayerController) -> Unit,
    onAudioDelete: (Long) -> Unit,
    selectedAudioId: Long? = null,
    playingAudioId: Long? = null,
) {
    val selectAudioLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            // todo: handle uri
        }
    )

    Column {
        AudioCategoriesRow(
            categories = audioCategories,
            onCategoryClick = { },
            firstCategoryLabel = "все",
            activeCategoryId = activeCategoryId,
            maxLines = 1,
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = LocalSpacing.current.small)
        )

        AudioItemsColumn(
            audioItems = audioItems,
            selectedAudioId = selectedAudioId,
            playingAudioId = playingAudioId,
            onAudioClick = { onPlaylistController(PlayerController.SelectMedia(it)) },
            onAudioDelete = onAudioDelete
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AddFloatingActionButton(
            onClick = { selectAudioLauncher.launch("audio/mp3") },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(LocalSpacing.current.medium)
        )
    }

}

@Preview(showSystemUi = true)
@Composable
fun AudioListContentPreview() {
    val audioItems = (1L..10L).map {
        dummyAudio.copy(id = it)
    }

    AudioListContent(
        audioCategories = ResultContainer.Done(
            (1..5).map {
                AudioCategory(it.toLong(), "Category $it")
            }
        ),
        audioItems = ResultContainer.Done(audioItems),
        onPlaylistController = {},
        onAudioDelete = {},
        selectedAudioId = 3,
        playingAudioId = 3
    )
}


