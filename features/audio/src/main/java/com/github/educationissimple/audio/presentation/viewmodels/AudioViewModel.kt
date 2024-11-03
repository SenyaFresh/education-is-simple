package com.github.educationissimple.audio.presentation.viewmodels

import com.github.educationissimple.audio.domain.entities.Audio
import com.github.educationissimple.audio.domain.entities.AudioCategory
import com.github.educationissimple.audio.domain.entities.AudioListState
import com.github.educationissimple.audio.domain.entities.PlayerController
import com.github.educationissimple.audio.domain.usecases.data.AddAudioUseCase
import com.github.educationissimple.audio.domain.usecases.data.DeleteAudioUseCase
import com.github.educationissimple.audio.domain.usecases.data.GetAudioItemsUseCase
import com.github.educationissimple.audio.domain.usecases.player.ChangeSelectedAudioUseCase
import com.github.educationissimple.audio.domain.usecases.player.ControlAudioUseCase
import com.github.educationissimple.audio.domain.usecases.player.GetPlayerStateUseCase
import com.github.educationissimple.audio.presentation.events.AudioEvent
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.presentation.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AudioViewModel(
    private val getAudioItemsUseCase: GetAudioItemsUseCase,
    private val addAudioUseCase: AddAudioUseCase,
    private val deleteAudioUseCase: DeleteAudioUseCase,
    private val changeSelectedAudioUseCase: ChangeSelectedAudioUseCase,
    private val controlAudioUseCase: ControlAudioUseCase,
    private val getPlayerStateUseCase: GetPlayerStateUseCase
): BaseViewModel() {

    private val _audioCategories = MutableStateFlow<ResultContainer<List<AudioCategory>>>(ResultContainer.Loading)
    val audioCategories = _audioCategories.asStateFlow()

    private val _audioItems = MutableStateFlow<ResultContainer<List<Audio>>>(ResultContainer.Loading)
    val audioItems = _audioItems.asStateFlow()

    private val _activeCategoryId = MutableStateFlow<ResultContainer<Long>>(ResultContainer.Loading)
    val activeCategoryId = _activeCategoryId.asStateFlow()

    private var _audioListState = MutableStateFlow<ResultContainer<AudioListState>>(ResultContainer.Loading)
    val audioListState = _audioListState.asStateFlow()

    init {
        collectAudioItems()
        collectPlayerState()
    }

    fun onEvent(event: AudioEvent) {
        when(event) {
            is AudioEvent.AddAudioItemEvent -> onAddAudioItemEvent(event.audio)
            is AudioEvent.DeleteAudioItemEvent -> onDeleteAudioItemEvent(event.id)
            is AudioEvent.PlayerEvent -> onPlayerEvent(event.controller)
        }
    }

    private fun collectAudioItems() = viewModelScope.launch {
        getAudioItemsUseCase.getAudioItems().collect {
            _audioItems.value = it
        }
    }

    private fun collectPlayerState() = viewModelScope.launch {
        getPlayerStateUseCase.getPlayerState().collect {
            _audioListState.value = it
        }
    }

    private fun onAddAudioItemEvent(audio: Audio) = viewModelScope.launch{
        addAudioUseCase.addAudioItem(audio)
    }

    private fun onDeleteAudioItemEvent(id: Long) = viewModelScope.launch {
        deleteAudioUseCase.deleteAudioItem(id)
    }

    private fun onPlayerEvent(controller: PlayerController) = viewModelScope.launch {
        when (controller) {
            is PlayerController.Close -> changeSelectedAudioUseCase.close()
            is PlayerController.Next -> changeSelectedAudioUseCase.next()
            is PlayerController.Previous -> changeSelectedAudioUseCase.previous()
            is PlayerController.SelectMedia -> changeSelectedAudioUseCase.changeSelectedAudio(controller.id)
            is PlayerController.PlayPause -> controlAudioUseCase.playPause()
            is PlayerController.SetPosition -> controlAudioUseCase.setPosition(controller.position)
        }
    }
}