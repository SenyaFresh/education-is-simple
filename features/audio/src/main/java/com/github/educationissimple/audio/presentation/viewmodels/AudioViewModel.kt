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

    private val _audioCategories =
        MutableStateFlow<ResultContainer<List<AudioCategory>>>(ResultContainer.Loading)
    val audioCategories = _audioCategories.asStateFlow()

    private val _audioItems =
        MutableStateFlow<ResultContainer<List<Audio>>>(ResultContainer.Loading)
    val audioItems = _audioItems.asStateFlow()

    private val _currentAudioItem = MutableStateFlow<Audio?>(null)
    val currentAudioItem = _currentAudioItem

    private val _activeCategoryId =
        MutableStateFlow<ResultContainer<Long?>>(ResultContainer.Loading)
    val activeCategoryId = _activeCategoryId.asStateFlow()

    private var _audioListState =
        MutableStateFlow<ResultContainer<AudioListState>>(ResultContainer.Loading)
    val audioListState = _audioListState.asStateFlow()

    private var isPlayerInitialized = false

    init {
        collectAudioItems()
        collectPlayerState()
        collectAudioCategories()
        collectActiveCategoryId()
    }

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

    private fun onReloadAudioItems() = viewModelScope.launch {
        getAudioItemsUseCase.reloadAudioItems()
    }

    private fun onReloadAudioCategories() = viewModelScope.launch {
        getCategoriesUseCase.reloadCategories()
    }

    private fun collectAudioItems() = viewModelScope.launch {
        getAudioItemsUseCase.getAudioItems().collect {
            _audioItems.value = it
            if (!isPlayerInitialized && it is ResultContainer.Done) {
                isPlayerInitialized = true
                initPlayerUseCase.initPlayer(it.unwrap())
            }
        }
    }

    private fun collectAudioCategories() = viewModelScope.launch {
        getCategoriesUseCase.getCategories().collect {
            _audioCategories.value = it
        }
    }

    private fun collectPlayerState() = viewModelScope.launch {
        getPlayerStateUseCase.getPlayerState().collect { state ->
            _audioListState.value = state
            _currentAudioItem.value =
                _audioItems.value.unwrapOrNull()?.find { it.uri == state.unwrap().currentAudioUri }
        }
    }

    private fun collectActiveCategoryId() = viewModelScope.launch {
        getCategoriesUseCase.getSelectedCategoryId().collect {
            _activeCategoryId.value = it
        }
    }

    private fun onAddAudioItemEvent(uri: String, categoryId: Long?) = viewModelScope.launch {
        addAudioToRepoUseCase.addAudioItem(uri, categoryId)
        addAudioToPlayerUseCase.addAudio(uri)
    }

    private fun onDeleteAudioItemEvent(uri: String) = viewModelScope.launch {
        deleteAudioFromRepoUseCase.deleteAudioItem(uri)
        deleteAudioFromPlayerUseCase.deleteAudio(getIndexByUri(uri) ?: -1)
    }

    private fun onAddCategoryEvent(name: String) = viewModelScope.launch {
        addAudioCategoryUseCase.addCategory(name)
    }

    private fun onDeleteCategoryEvent(categoryId: Long) = viewModelScope.launch {
        deleteAudioCategoryUseCase.deleteCategory(categoryId)
    }

    private fun onChangeCategoryEvent(categoryId: Long?) = viewModelScope.launch {
        getAudioItemsUseCase.changeSelectedCategoryId(categoryId)
    }

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

    private fun getIndexByUri(uri: String): Int? {
        return if (audioItems.value is ResultContainer.Done) audioItems.value.unwrap()
            .indexOfFirst { it.uri == uri }
        else null
    }

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