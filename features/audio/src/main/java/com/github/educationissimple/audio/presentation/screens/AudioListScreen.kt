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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.educationissimple.audio.di.AudioDiContainer
import com.github.educationissimple.audio.di.rememberAudioDiContainer
import com.github.educationissimple.audio.domain.entities.Audio
import com.github.educationissimple.audio.domain.entities.AudioCategory
import com.github.educationissimple.audio.domain.entities.AudioCategory.Companion.NO_CATEGORY_ID
import com.github.educationissimple.audio.domain.entities.AudioListState
import com.github.educationissimple.audio.domain.entities.PlayerController
import com.github.educationissimple.audio.presentation.components.lists.AudioCategoriesRow
import com.github.educationissimple.audio.presentation.components.lists.AudioItemsColumn
import com.github.educationissimple.audio.presentation.entities.dummies.dummyAudio
import com.github.educationissimple.audio.presentation.events.AudioEvent
import com.github.educationissimple.audio.presentation.viewmodels.AudioViewModel
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.components.composables.AddFloatingActionButton
import com.github.educationissimple.presentation.ResultContainerComposable
import com.github.educationissimple.presentation.locals.LocalSpacing

@Composable
fun AudioListScreen(
    diContainer: AudioDiContainer = rememberAudioDiContainer(),
    viewModel: AudioViewModel = viewModel(factory = diContainer.viewModelFactory)
) {
    AudioListContent(
        audioCategories = ResultContainer.Done(listOf()), // todo: implement categories
        audioItems = viewModel.audioItems.collectAsStateWithLifecycle().value,
        activeCategoryId = ResultContainer.Done(NO_CATEGORY_ID), // todo
        audioListState = viewModel.audioListState.collectAsStateWithLifecycle().value,
        onAudioEvent = viewModel::onEvent
    )
}

@Composable
fun AudioListContent(
    audioCategories: ResultContainer<List<AudioCategory>>,
    audioItems: ResultContainer<List<Audio>>,
    activeCategoryId: ResultContainer<Long>,
    audioListState: ResultContainer<AudioListState>,
    onAudioEvent: (AudioEvent) -> Unit
) {
    val selectAudioLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let { onAudioEvent(AudioEvent.AddAudioItemEvent(it.toString())) }
        }
    )

    ResultContainerComposable(
        onTryAgain = {},
        container = ResultContainer.wrap(
            audioCategories,
            audioItems,
            activeCategoryId
        )
    ) {
        Column {
            AudioCategoriesRow(
                categories = audioCategories,
                onCategoryClick = { },
                firstCategoryLabel = "все",
                activeCategoryId = activeCategoryId.unwrap(),
                maxLines = 1,
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = LocalSpacing.current.small)
            )

            AudioItemsColumn(
                audioItems = audioItems,
                selectedAudioUri =
                    if (audioListState.unwrapOrNull()?.state == AudioListState.State.IDLE) null
                    else audioListState.unwrapOrNull()?.currentAudioUri,
                playingAudioUri =
                    if (audioListState.unwrapOrNull()?.state != AudioListState.State.AUDIO_PLAYING) null
                    else audioListState.unwrapOrNull()?.currentAudioUri,
                onAudioClick = { onAudioEvent(AudioEvent.PlayerEvent(PlayerController.SelectMedia(it))) },
                onAudioDelete = { onAudioEvent(AudioEvent.DeleteAudioItemEvent(it)) }
            )
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AddFloatingActionButton(
            onClick = { selectAudioLauncher.launch("audio/mpeg") },
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
        dummyAudio.copy(uri = it.toString())
    }

    AudioListContent(
        audioCategories = ResultContainer.Done(
            (1..5).map {
                AudioCategory(it.toLong(), "Category $it")
            }
        ),
        audioItems = ResultContainer.Done(audioItems),
        activeCategoryId = ResultContainer.Done(NO_CATEGORY_ID),
        audioListState = ResultContainer.Done(
            AudioListState(
                state = AudioListState.State.AUDIO_PLAYING,
                currentAudioUri = "",
                positionMs = 12,
                durationMs = 100
            )
        ),
        onAudioEvent = {}
    )
}


