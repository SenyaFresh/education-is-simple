package com.github.educationissimple.tasks.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.presentation.BaseViewModel
import com.github.educationissimple.tasks.domain.entities.Task
import com.github.educationissimple.tasks.domain.usecases.AddTaskUseCase
import com.github.educationissimple.tasks.domain.usecases.CancelTaskUseCase
import com.github.educationissimple.tasks.domain.usecases.CompleteTaskUseCase
import com.github.educationissimple.tasks.domain.usecases.DeleteTaskUseCase
import com.github.educationissimple.tasks.domain.usecases.GetTasksUseCase
import com.github.educationissimple.tasks.presentation.events.TasksEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TasksViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    private val cancelTaskUseCase: CancelTaskUseCase,
    private val completeTaskUseCase: CompleteTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val getTasksUseCase: GetTasksUseCase
) : BaseViewModel() {

    private val _previousTasks =
        MutableStateFlow<ResultContainer<List<Task>>>(ResultContainer.Loading)
    val previousTasks = _previousTasks.asStateFlow()

    private val _todayTasks = MutableStateFlow<ResultContainer<List<Task>>>(ResultContainer.Loading)
    val todayTasks = _todayTasks.asStateFlow()

    private val _futureTasks = MutableStateFlow<ResultContainer<List<Task>>>(ResultContainer.Loading)
    val futureTasks = _futureTasks.asStateFlow()

    private val _completedTasks = MutableStateFlow<ResultContainer<List<Task>>>(ResultContainer.Loading)
    val completedTasks = _completedTasks.asStateFlow()

    init {
        collectPreviousTasks()
        collectTodayTasks()
        collectFutureTasks()
        collectCompletedTasks()
    }

    fun onEvent(event: TasksEvent) = debounce {
        when (event) {
            is TasksEvent.AddTask -> addTask(event.task)
            is TasksEvent.CancelTaskCompletion -> cancelTask(event.taskId)
            is TasksEvent.CompleteTask -> completeTask(event.taskId)
            is TasksEvent.DeleteTask -> deleteTask(event.taskId)
        }
    }

    private fun addTask(task: Task) {
        viewModelScope.launch {
            addTaskUseCase.addTask(task)
        }
    }

    private fun cancelTask(taskId: Long) {
        viewModelScope.launch {
            cancelTaskUseCase.cancelTask(taskId)
        }
    }

    private fun completeTask(taskId: Long) {
        viewModelScope.launch {
            completeTaskUseCase.completeTask(taskId)
        }
    }

    private fun deleteTask(taskId: Long) {
        viewModelScope.launch {
            deleteTaskUseCase.deleteTask(taskId)
        }
    }

    private fun collectPreviousTasks() {
        viewModelScope.launch {
            getTasksUseCase.getPreviousTasks().collect {
                _previousTasks.value = it
            }
        }
    }

    private fun collectTodayTasks() {
        viewModelScope.launch {
            getTasksUseCase.getTodayTasks().collect {
                _todayTasks.value = it
            }
        }
    }

    private fun collectFutureTasks() {
        viewModelScope.launch {
            getTasksUseCase.getFutureTasks().collect {
                _futureTasks.value = it
            }
        }
    }

    private fun collectCompletedTasks() {
        viewModelScope.launch {
            getTasksUseCase.getCompletedTasks().collect {
                _completedTasks.value = it
            }
        }
    }


    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val addTaskUseCase: AddTaskUseCase,
        private val cancelTaskUseCase: CancelTaskUseCase,
        private val completeTaskUseCase: CompleteTaskUseCase,
        private val deleteTaskUseCase: DeleteTaskUseCase,
        private val getTasksUseCase: GetTasksUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == TasksViewModel::class.java)
            return TasksViewModel(
                addTaskUseCase,
                cancelTaskUseCase,
                completeTaskUseCase,
                deleteTaskUseCase,
                getTasksUseCase
            ) as T
        }
    }

}