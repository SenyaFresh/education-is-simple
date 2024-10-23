package com.github.educationissimple.tasks.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.presentation.BaseViewModel
import com.github.educationissimple.tasks.domain.entities.SortType
import com.github.educationissimple.tasks.domain.entities.Task
import com.github.educationissimple.tasks.domain.entities.TaskCategory
import com.github.educationissimple.tasks.domain.usecases.AddCategoryUseCase
import com.github.educationissimple.tasks.domain.usecases.AddTaskUseCase
import com.github.educationissimple.tasks.domain.usecases.ChangeCategoryUseCase
import com.github.educationissimple.tasks.domain.usecases.ChangeSortTypeUseCase
import com.github.educationissimple.tasks.domain.usecases.DeleteCategoryUseCase
import com.github.educationissimple.tasks.domain.usecases.DeleteTaskUseCase
import com.github.educationissimple.tasks.domain.usecases.GetCategoriesUseCase
import com.github.educationissimple.tasks.domain.usecases.GetTasksUseCase
import com.github.educationissimple.tasks.domain.usecases.UpdateTaskUseCase
import com.github.educationissimple.tasks.presentation.events.TasksEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TasksViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val getTasksUseCase: GetTasksUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val changeCategoryUseCase: ChangeCategoryUseCase,
    private val changeSortTypeUseCase: ChangeSortTypeUseCase,
    private val addCategoryUseCase: AddCategoryUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase
) : BaseViewModel() {

    private val _previousTasks =
        MutableStateFlow<ResultContainer<List<Task>>>(ResultContainer.Loading)
    val previousTasks = _previousTasks.asStateFlow()

    private val _todayTasks = MutableStateFlow<ResultContainer<List<Task>>>(ResultContainer.Loading)
    val todayTasks = _todayTasks.asStateFlow()

    private val _futureTasks =
        MutableStateFlow<ResultContainer<List<Task>>>(ResultContainer.Loading)
    val futureTasks = _futureTasks.asStateFlow()

    private val _completedTasks =
        MutableStateFlow<ResultContainer<List<Task>>>(ResultContainer.Loading)
    val completedTasks = _completedTasks.asStateFlow()

    private val _categories =
        MutableStateFlow<ResultContainer<List<TaskCategory>>>(ResultContainer.Loading)
    val categories = _categories.asStateFlow()

    init {
        collectPreviousTasks()
        collectTodayTasks()
        collectFutureTasks()
        collectCompletedTasks()
        collectCategories()
    }

    fun onEvent(event: TasksEvent) = debounce {
        when (event) {
            is TasksEvent.AddTask -> addTask(event.task)
            is TasksEvent.ChangeTaskPriority -> changeTaskPriority(event.taskId, event.priority)
            is TasksEvent.ChangeSortType -> changeSortType(event.sortType)
            is TasksEvent.CancelTaskCompletion -> cancelTaskCompletion(event.taskId)
            is TasksEvent.CompleteTask -> completeTask(event.taskId)
            is TasksEvent.DeleteTask -> deleteTask(event.taskId)
            is TasksEvent.ChangeCategory -> changeCategory(event.categoryId)
            is TasksEvent.AddCategory -> addCategory(event.name)
            is TasksEvent.DeleteCategory -> deleteCategory(event.categoryId)
        }
    }

    private fun changeSortType(sortType: SortType?) {
        viewModelScope.launch {
            changeSortTypeUseCase.changeSortType(sortType)
        }
    }

    private fun addTask(task: Task) {
        viewModelScope.launch {
            addTaskUseCase.addTask(task)
        }
    }

    private fun changeTaskPriority(taskId: Long, priority: Task.Priority) {
        viewModelScope.launch {
            val task = previousTasks.value.unwrapOrNull()?.find { it.id == taskId } ?:
            todayTasks.value.unwrapOrNull()?.find { it.id == taskId } ?:
            futureTasks.value.unwrapOrNull()?.find { it.id == taskId } ?:
            completedTasks.value.unwrapOrNull()?.find { it.id == taskId } ?: return@launch

            updateTaskUseCase.updateTask(task.copy(priority = priority))
        }
    }

    private fun cancelTaskCompletion(taskId: Long) {
        viewModelScope.launch {
            val task = completedTasks.value.unwrapOrNull()?.find { it.id == taskId } ?: return@launch
            updateTaskUseCase.updateTask(task.copy(isCompleted = false))
        }
    }

    private fun completeTask(taskId: Long) {
        viewModelScope.launch {
            val task = previousTasks.value.unwrapOrNull()?.find { it.id == taskId } ?:
                todayTasks.value.unwrapOrNull()?.find { it.id == taskId } ?:
                futureTasks.value.unwrapOrNull()?.find { it.id == taskId } ?: return@launch

            updateTaskUseCase.updateTask(task.copy(isCompleted = true))
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

    private fun collectCategories() {
        viewModelScope.launch {
            getCategoriesUseCase.getCategories().collect {
                _categories.value = it
            }
        }
    }

    private fun changeCategory(categoryId: Long?) {
        viewModelScope.launch {
            changeCategoryUseCase.changeCategory(categoryId)
        }
    }

    private fun addCategory(name: String) {
        viewModelScope.launch {
            addCategoryUseCase.addCategory(name)
        }
    }

    private fun deleteCategory(categoryId: Long) {
        viewModelScope.launch {
            deleteCategoryUseCase.deleteCategory(categoryId)
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val addTaskUseCase: AddTaskUseCase,
        private val updateTaskUseCase: UpdateTaskUseCase,
        private val deleteTaskUseCase: DeleteTaskUseCase,
        private val getTasksUseCase: GetTasksUseCase,
        private val getCategoriesUseCase: GetCategoriesUseCase,
        private val changeCategoryUseCase: ChangeCategoryUseCase,
        private val changeSortTypeUseCase: ChangeSortTypeUseCase,
        private val addCategoryUseCase: AddCategoryUseCase,
        private val deleteCategoryUseCase: DeleteCategoryUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == TasksViewModel::class.java)
            return TasksViewModel(
                addTaskUseCase,
                updateTaskUseCase,
                deleteTaskUseCase,
                getTasksUseCase,
                getCategoriesUseCase,
                changeCategoryUseCase,
                changeSortTypeUseCase,
                addCategoryUseCase,
                deleteCategoryUseCase
            ) as T
        }
    }
}