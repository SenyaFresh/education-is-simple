package com.github.educationissimple.tasks.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.presentation.BaseViewModel
import com.github.educationissimple.tasks.domain.entities.SortType
import com.github.educationissimple.tasks.domain.entities.Task
import com.github.educationissimple.tasks.domain.entities.TaskCategory
import com.github.educationissimple.tasks.domain.usecases.categories.AddCategoryUseCase
import com.github.educationissimple.tasks.domain.usecases.categories.DeleteCategoryUseCase
import com.github.educationissimple.tasks.domain.usecases.categories.GetCategoriesUseCase
import com.github.educationissimple.tasks.domain.usecases.tasks.AddTaskUseCase
import com.github.educationissimple.tasks.domain.usecases.tasks.DeleteTaskUseCase
import com.github.educationissimple.tasks.domain.usecases.tasks.GetTasksUseCase
import com.github.educationissimple.tasks.domain.usecases.tasks.UpdateTaskUseCase
import com.github.educationissimple.tasks.presentation.events.TasksEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

class TasksViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val getTasksUseCase: GetTasksUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val addCategoryUseCase: AddCategoryUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase
) : BaseViewModel() {

    private val _sortType = MutableStateFlow<ResultContainer<SortType?>>(ResultContainer.Loading)
    val sortType = _sortType.asStateFlow()

    private val _activeCategoryId =
        MutableStateFlow<ResultContainer<Long?>>(ResultContainer.Loading)
    val activeCategoryId = _activeCategoryId.asStateFlow()

    private val _notCompletedTasksWithDate =
        MutableStateFlow<ResultContainer<List<Task>>>(ResultContainer.Loading)
    val notCompletedTasksWithDate = _notCompletedTasksWithDate.asStateFlow()

    private val _completedTasksWithDate =
        MutableStateFlow<ResultContainer<List<Task>>>(ResultContainer.Loading)
    val completedTasksWithDate = _completedTasksWithDate.asStateFlow()

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
        collectNotCompletedTasksWithDate()
        collectCompletedTasksWithDate()
        collectPreviousTasks()
        collectTodayTasks()
        collectFutureTasks()
        collectCompletedTasks()
        collectCategories()
        collectSelectedSortType()
        collectSelectedCategoryId()
    }

    fun onEvent(event: TasksEvent) = debounce {
        when (event) {
            is TasksEvent.AddTask -> addTask(event.task)
            is TasksEvent.ChangeSortType -> changeSortType(event.sortType)
            is TasksEvent.DeleteTask -> deleteTask(event.taskId)
            is TasksEvent.UpdateTask -> updateTask(event.updatedTask)
            is TasksEvent.ChangeCategory -> changeCategory(event.categoryId)
            is TasksEvent.AddCategory -> addCategory(event.name)
            is TasksEvent.DeleteCategory -> deleteCategory(event.categoryId)
            is TasksEvent.ChangeTaskSearchText -> changeTaskSearchText(event.text)
            is TasksEvent.ChangeTasksSelectionDate -> changeSelectionDate(event.date)
        }
    }

    private fun updateTask(updatedTask: Task) {
        viewModelScope.launch {
            updateTaskUseCase.updateTask(updatedTask)
        }
    }

    private fun changeSelectionDate(date: LocalDate) {
        viewModelScope.launch {
            getTasksUseCase.changeSelectionDate(date)
        }
    }

    private fun changeSortType(sortType: SortType?) {
        viewModelScope.launch {
            getTasksUseCase.changeSortType(sortType)
        }
    }

    private fun addTask(task: Task) {
        viewModelScope.launch {
            addTaskUseCase.addTask(task)
        }
    }

    private fun deleteTask(taskId: Long) {
        viewModelScope.launch {
            deleteTaskUseCase.deleteTask(taskId)
        }
    }

    private fun changeTaskSearchText(text: String) {
        viewModelScope.launch {
            getTasksUseCase.changeTaskSearchText(text)
        }
    }

    private fun collectNotCompletedTasksWithDate() {
        viewModelScope.launch {
            getTasksUseCase.getNotCompletedTasksForDate().collect {
                _notCompletedTasksWithDate.value = it
            }
        }
    }

    private fun collectCompletedTasksWithDate() {
        viewModelScope.launch {
            getTasksUseCase.getCompletedTasksForDate().collect {
                _completedTasksWithDate.value = it
            }
        }
    }

    private fun collectSelectedSortType() {
        viewModelScope.launch {
            getTasksUseCase.getSelectedSortType().collect {
                _sortType.value = it
            }
        }
    }

    private fun collectSelectedCategoryId() {
        viewModelScope.launch {
            getCategoriesUseCase.getSelectedCategoryId().collect {
                _activeCategoryId.value = it
            }
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
            getTasksUseCase.changeCategory(categoryId)
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
                addCategoryUseCase,
                deleteCategoryUseCase
            ) as T
        }
    }
}