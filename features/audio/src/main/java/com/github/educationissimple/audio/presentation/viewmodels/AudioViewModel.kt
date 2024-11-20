package com.github.educationissimple.audio.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.educationissimple.audio.domain.entities.Audio
import com.github.educationissimple.audio.domain.entities.AudioCategory
import com.github.educationissimple.audio.domain.entities.AudioListState
import com.github.educationissimple.audio.domain.entities.PlayerController
import com.github.educationissimple.audio.domain.usecases.categories.AddCategoryUseCase
import com.github.educationissimple.audio.domain.usecases.categories.DeleteCategoryUseCase
import com.github.educationissimple.audio.domain.usecases.categories.GetCategoriesUseCase
import com.github.educationissimple.audio.domain.usecases.data.AddAudioToRepoUseCase
import com.github.educationissimple.audio.domain.usecases.data.DeleteAudioFromRepoUseCase
import com.github.educationissimple.audio.domain.usecases.data.GetAudioItemsUseCase
import com.github.educationissimple.audio.domain.usecases.player.AddAudioToPlayerUseCase
import com.github.educationissimple.audio.domain.usecases.player.ChangeSelectedAudioUseCase
import com.github.educationissimple.audio.domain.usecases.player.ControlAudioUseCase
import com.github.educationissimple.audio.domain.usecases.player.DeleteAudioFromPlayerUseCase
import com.github.educationissimple.audio.domain.usecases.player.GetPlayerStateUseCase
import com.github.educationissimple.audio.domain.usecases.player.InitPlayerUseCase
import com.github.educationissimple.audio.presentation.events.AudioEvent
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.presentation.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for managing audio items, audio categories, and player state for the audio app.
 *
 * This ViewModel interacts with use cases that handle audio item and category operations,
 * audio control, and player state management. It provides data streams for the audio items,
 * categories, the currently active category, and the current audio item. It also allows handling
 * user actions such as adding, deleting, or changing audio items and categories, as well as controlling
 * the audio player (play, pause, next, previous, etc.).
 **/
class AudioViewModel @Inject constructor(
    private val onStartAudioService: () -> Unit,
    private val onStopAudioService: () -> Unit,
    private val getAudioItemsUseCase: GetAudioItemsUseCase,
    private val addAudioToRepoUseCase: AddAudioToRepoUseCase,
    private val deleteAudioFromRepoUseCase: DeleteAudioFromRepoUseCase,
    private val changeSelectedAudioUseCase: ChangeSelectedAudioUseCase,
    private val controlAudioUseCase: ControlAudioUseCase,
    private val getPlayerStateUseCase: GetPlayerStateUseCase,
    private val addAudioToPlayerUseCase: AddAudioToPlayerUseCase,
    private val deleteAudioFromPlayerUseCase: DeleteAudioFromPlayerUseCase,
    private val initPlayerUseCase: InitPlayerUseCase,
    private val addAudioCategoryUseCase: AddCategoryUseCase,
    private val deleteAudioCategoryUseCase: DeleteCategoryUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : BaseViewModel() {

    /** The state flow of audio categories. */
    private val _audioCategories =
        MutableStateFlow<ResultContainer<List<AudioCategory>>>(ResultContainer.Loading)
    val audioCategories = _audioCategories.asStateFlow()

    /** The state flow of audio items. */
    private val _audioItems =
        MutableStateFlow<ResultContainer<List<Audio>>>(ResultContainer.Loading)
    val audioItems = _audioItems.asStateFlow()

    /** The current selected audio item. */
    private val _currentAudioItem = MutableStateFlow<Audio?>(null)
    val currentAudioItem = _currentAudioItem

    /** The state flow of the currently active category. */
    private val _activeCategoryId =
        MutableStateFlow<ResultContainer<Long?>>(ResultContainer.Loading)
    val activeCategoryId = _activeCategoryId.asStateFlow()

    /** The state flow of the current audio list state. */
    private var _audioListState =
        MutableStateFlow<ResultContainer<AudioListState>>(ResultContainer.Loading)
    val audioListState = _audioListState.asStateFlow()

    /** Flag to check if the audio player has been initialized. */
    private var isPlayerInitialized = false

    init {
        collectAudioItems()
        collectPlayerState()
        collectAudioCategories()
        collectActiveCategoryId()
    }

    /** Handles incoming audio events like adding, deleting items, or controlling the player. */
    fun onEvent(event: AudioEvent) = debounce {
        when (event) {
            is AudioEvent.AddAudioItemEvent -> onAddAudioItemEvent(event.uri, event.categoryId)
            is AudioEvent.DeleteAudioItemEvent -> onDeleteAudioItemEvent(event.uri)
            is AudioEvent.PlayerEvent -> onPlayerEvent(event.controller)
            is AudioEvent.CreateCategoryEvent -> onAddCategoryEvent(event.name)
            is AudioEvent.DeleteCategoryEvent -> onDeleteCategoryEvent(event.categoryId)
            is AudioEvent.ChangeCategoryEvent -> onChangeCategoryEvent(event.categoryId)
            is AudioEvent.ReloadAudioItemsEvent -> onReloadAudioItems()
            is AudioEvent.ReloadAudioCategoriesEvent -> onReloadAudioCategories()
        }
    }

    /** Reloads the audio items from the use case. */
    private fun onReloadAudioItems() = viewModelScope.launch {
        getAudioItemsUseCase.reloadAudioItems()
    }

    /** Reloads the audio categories from the use case. */
    private fun onReloadAudioCategories() = viewModelScope.launch {
        getCategoriesUseCase.reloadCategories()
    }

    /** Collects the audio items and initializes the audio player if not initialized. */
    private fun collectAudioItems() = viewModelScope.launch {
        getAudioItemsUseCase.getAudioItems().collect {
            _audioItems.value = it
            if (!isPlayerInitialized && it is ResultContainer.Done) {
                isPlayerInitialized = true
                initPlayerUseCase.initPlayer(it.unwrap())
            }
        }
    }

    /** Collects the audio categories. */
    private fun collectAudioCategories() = viewModelScope.launch {
        getCategoriesUseCase.getCategories().collect {
            _audioCategories.value = it
        }
    }

    /** Collects the player state. */
    private fun collectPlayerState() = viewModelScope.launch {
        getPlayerStateUseCase.getPlayerState().collect { state ->
            _audioListState.value = state
            _currentAudioItem.value =
                _audioItems.value.unwrapOrNull()?.find { it.uri == state.unwrap().currentAudioUri }
        }
    }

    /** Collects the currently active category ID. */
    private fun collectActiveCategoryId() = viewModelScope.launch {
        getCategoriesUseCase.getSelectedCategoryId().collect {
            _activeCategoryId.value = it
        }
    }

    /** Handles adding an audio item to the repository and the audio player. */
    private fun onAddAudioItemEvent(uri: String, categoryId: Long?) = viewModelScope.launch {
        addAudioToRepoUseCase.addAudioItem(uri, categoryId)
        addAudioToPlayerUseCase.addAudio(uri)
    }

    /** Handles deleting an audio item from the repository and the audio player. */
    private fun onDeleteAudioItemEvent(uri: String) = viewModelScope.launch {
        deleteAudioFromRepoUseCase.deleteAudioItem(uri)
        deleteAudioFromPlayerUseCase.deleteAudio(getIndexByUri(uri) ?: -1)
    }

    /** Handles adding a new audio category. */
    private fun onAddCategoryEvent(name: String) = viewModelScope.launch {
        addAudioCategoryUseCase.addCategory(name)
    }

    /** Handles deleting an audio category. */
    private fun onDeleteCategoryEvent(categoryId: Long) = viewModelScope.launch {
        deleteAudioCategoryUseCase.deleteCategory(categoryId)
    }

    /** Handles changing the currently active category. */
    private fun onChangeCategoryEvent(categoryId: Long?) = viewModelScope.launch {
        getAudioItemsUseCase.changeSelectedCategoryId(categoryId)
    }

    /** Handles incoming player events. */
    private fun onPlayerEvent(controller: PlayerController) = viewModelScope.launch {
        when (controller) {
            is PlayerController.Close -> {
                changeSelectedAudioUseCase.close()
                onStopAudioService()
            }

            is PlayerController.Next -> changeSelectedAudioUseCase.next()
            is PlayerController.Previous -> changeSelectedAudioUseCase.previous()
            is PlayerController.SelectMedia -> getIndexByUri(controller.uri)?.let {
                onStartAudioService()
                changeSelectedAudioUseCase.changeSelectedAudio(it)
            }

            is PlayerController.PlayPause -> controlAudioUseCase.playPause()
            is PlayerController.SetPosition -> controlAudioUseCase.setPosition(controller.positionMs)
        }
    }

    /** Retrieves the index of an audio item by its URI. */
    private fun getIndexByUri(uri: String): Int? {
        return if (audioItems.value is ResultContainer.Done) audioItems.value.unwrap()
            .indexOfFirst { it.uri == uri }
        else null
    }

    /**
     * Factory for creating instances of [AudioViewModel] with assisted injection.
     */
    @Suppress("UNCHECKED_CAST")
    class Factory @AssistedInject constructor(
        @Assisted("onStartAudioService") private val onStartAudioService: () -> Unit,
        @Assisted("onStopAudioService") private val onStopAudioService: () -> Unit,
        private val getAudioItemsUseCase: GetAudioItemsUseCase,
        private val addAudioToRepoUseCase: AddAudioToRepoUseCase,
        private val deleteAudioFromRepoUseCase: DeleteAudioFromRepoUseCase,
        private val changeSelectedAudioUseCase: ChangeSelectedAudioUseCase,
        private val controlAudioUseCase: ControlAudioUseCase,
        private val getPlayerStateUseCase: GetPlayerStateUseCase,
        private val addAudioToPlayerUseCase: AddAudioToPlayerUseCase,
        private val deleteAudioFromPlayerUseCase: DeleteAudioFromPlayerUseCase,
        private val initPlayerUseCase: InitPlayerUseCase,
        private val addAudioCategoryUseCase: AddCategoryUseCase,
        private val deleteAudioCategoryUseCase: DeleteCategoryUseCase,
        private val getCategoriesUseCase: GetCategoriesUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == AudioViewModel::class.java)
            return AudioViewModel(
                onStartAudioService,
                onStopAudioService,
                getAudioItemsUseCase,
                addAudioToRepoUseCase,
                deleteAudioFromRepoUseCase,
                changeSelectedAudioUseCase,
                controlAudioUseCase,
                getPlayerStateUseCase,
                addAudioToPlayerUseCase,
                deleteAudioFromPlayerUseCase,
                initPlayerUseCase,
                addAudioCategoryUseCase,
                deleteAudioCategoryUseCase,
                getCategoriesUseCase
            ) as T
        }

        @AssistedFactory
        interface Factory {
            fun create(
                @Assisted("onStartAudioService") onStartAudioService: () -> Unit = {},
                @Assisted("onStopAudioService") onStopAudioService: () -> Unit = {}
            ): AudioViewModel.Factory
        }
    }
}