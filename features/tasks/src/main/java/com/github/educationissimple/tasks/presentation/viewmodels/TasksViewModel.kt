package com.github.educationissimple.tasks.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.educationissimple.common.ResultContainer
import com.github.educationissimple.presentation.BaseViewModel
import com.github.educationissimple.tasks.domain.entities.SortType
import com.github.educationissimple.tasks.domain.entities.Task
import com.github.educationissimple.tasks.domain.entities.TaskCategory
import com.github.educationissimple.tasks.domain.entities.TaskReminder
import com.github.educationissimple.tasks.domain.usecases.categories.AddCategoryUseCase
import com.github.educationissimple.tasks.domain.usecases.categories.DeleteCategoryUseCase
import com.github.educationissimple.tasks.domain.usecases.categories.GetCategoriesUseCase
import com.github.educationissimple.tasks.domain.usecases.reminders.CreateReminderUseCase
import com.github.educationissimple.tasks.domain.usecases.reminders.DeleteReminderUseCase
import com.github.educationissimple.tasks.domain.usecases.reminders.GetRemindersUseCase
import com.github.educationissimple.tasks.domain.usecases.tasks.AddTaskUseCase
import com.github.educationissimple.tasks.domain.usecases.tasks.DeleteTaskUseCase
import com.github.educationissimple.tasks.domain.usecases.tasks.GetTasksUseCase
import com.github.educationissimple.tasks.domain.usecases.tasks.UpdateTaskUseCase
import com.github.educationissimple.tasks.presentation.events.TasksEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

/**
 * ViewModel for managing tasks, categories, and reminders.
 *
 * This ViewModel is responsible for handling events such as adding, deleting, and updating tasks,
 * categories, and reminders. It also manages the state of various data (tasks, categories, reminders)
 * and interacts with the corresponding UseCases to execute the business logic.
 *
 */
class TasksViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val getTasksUseCase: GetTasksUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val addCategoryUseCase: AddCategoryUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase,
    private val getRemindersUseCase: GetRemindersUseCase,
    private val createReminderUseCase: CreateReminderUseCase,
    private val deleteReminderUseCase: DeleteReminderUseCase,
) : BaseViewModel() {

    /**
     * StateFlow for managing the sort type of tasks.
     * The sort type controls how tasks are ordered (e.g., by date, priority).
     */
    private val _sortType = MutableStateFlow<ResultContainer<SortType?>>(ResultContainer.Loading)
    val sortType = _sortType.asStateFlow()

    /**
     * StateFlow for managing the active category ID.
     * This ID represents the category of tasks currently being displayed.
     */
    private val _activeCategoryId =
        MutableStateFlow<ResultContainer<Long?>>(ResultContainer.Loading)
    val activeCategoryId = _activeCategoryId.asStateFlow()

    /**
     * StateFlow for managing tasks that are not completed for specific date.
     * This list holds tasks that are yet to be completed.
     */
    private val _notCompletedTasksWithDate =
        MutableStateFlow<ResultContainer<List<Task>>>(ResultContainer.Loading)
    val notCompletedTasksWithDate = _notCompletedTasksWithDate.asStateFlow()

    /**
     * StateFlow for managing tasks that are completed for specific date.
     * This list holds tasks that have been completed.
     */
    private val _completedTasksWithDate =
        MutableStateFlow<ResultContainer<List<Task>>>(ResultContainer.Loading)
    val completedTasksWithDate = _completedTasksWithDate.asStateFlow()

    /**
     * StateFlow for managing previous tasks.
     * This list holds tasks that have not been completed in the past.
     */
    private val _previousTasks =
        MutableStateFlow<ResultContainer<List<Task>>>(ResultContainer.Loading)
    val previousTasks = _previousTasks.asStateFlow()

    /**
     * StateFlow for managing today's tasks.
     * This list holds tasks that are due today.
     */
    private val _todayTasks = MutableStateFlow<ResultContainer<List<Task>>>(ResultContainer.Loading)
    val todayTasks = _todayTasks.asStateFlow()

    /**
     * StateFlow for managing future tasks.
     * This list holds tasks that are due in the future.
     */
    private val _futureTasks =
        MutableStateFlow<ResultContainer<List<Task>>>(ResultContainer.Loading)
    val futureTasks = _futureTasks.asStateFlow()

    /**
     * StateFlow for managing completed tasks.
     * This list holds tasks that have been completed.
     */
    private val _completedTasks =
        MutableStateFlow<ResultContainer<List<Task>>>(ResultContainer.Loading)
    val completedTasks = _completedTasks.asStateFlow()

    /**
     * StateFlow for managing categories.
     * This list holds all available categories.
     */
    private val _categories =
        MutableStateFlow<ResultContainer<List<TaskCategory>>>(ResultContainer.Loading)
    val categories = _categories.asStateFlow()

    /**
     * StateFlow for managing reminders.
     * This list holds all available reminders.
     */
    private val _reminders =
        MutableStateFlow<ResultContainer<List<TaskReminder>>>(ResultContainer.Loading)
    val reminders = _reminders.asStateFlow()

    /**
     * Map to store reminders for specific tasks.
     */
    private val _remindersForTasks =
        mutableMapOf<Long, MutableStateFlow<ResultContainer<List<TaskReminder>>>>()

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
        collectReminders()
    }

    /**
     * Processes events related to tasks, categories, reminders, and other actions.
     *
     * @param event The event that triggers changes in the ViewModel.
     */
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
            is TasksEvent.AddTaskReminder -> createReminder(event.taskReminder)
            is TasksEvent.DeleteTaskReminder -> deleteReminder(event.taskReminder)
            is TasksEvent.ReloadTasks -> reloadTasks()
            is TasksEvent.ReloadCategories -> reloadCategories()
            is TasksEvent.ReloadReminders -> reloadReminders()
        }
    }

    /**
     * Reloads all reminders from the data source.
     */
    private fun reloadReminders() = viewModelScope.launch {
        getRemindersUseCase.reloadReminders()
    }

    /**
     * Reloads all categories from the data source.
     */
    private fun reloadCategories() = viewModelScope.launch {
        getCategoriesUseCase.reloadCategories()
    }

    /**
     * Reloads all tasks from the data source.
     */
    private fun reloadTasks() = viewModelScope.launch {
        getTasksUseCase.reloadTasks()
    }

    /**
     * Fetches all reminders associated with a specific task.
     * @param taskId The ID of the task whose reminders are to be fetched.
     * @return A [Flow] emitting a [ResultContainer] containing a list of task reminders.
     */
    fun getRemindersForTask(taskId: Long): StateFlow<ResultContainer<List<TaskReminder>>> {
        if (_remindersForTasks[taskId] == null) {
            _remindersForTasks[taskId] = MutableStateFlow(ResultContainer.Loading)
            debounce {
                viewModelScope.launch {
                    getRemindersUseCase.getRemindersForTask(taskId).collect {
                        _remindersForTasks[taskId]!!.value = it
                    }
                }

            }
        }
        return _remindersForTasks[taskId]!!.asStateFlow()
    }

    /**
     * Creates a new task reminder.
     * @param reminder The task reminder to be created.
     */
    private fun createReminder(reminder: TaskReminder) {
        viewModelScope.launch {
            createReminderUseCase.createReminder(reminder)
        }
    }

    /**
     * Deletes a task reminder.
     * @param reminder The task reminder to be deleted.
     */
    private fun deleteReminder(reminder: TaskReminder) {
        viewModelScope.launch {
            deleteReminderUseCase.deleteReminder(reminder)
        }
    }

    /**
     * Updates an existing task.
     * @param updatedTask The updated task.
     */
    private fun updateTask(updatedTask: Task) {
        viewModelScope.launch {
            updateTaskUseCase.updateTask(updatedTask)
        }
    }

    /**
     * Changes the selection date for tasks.
     * @param date The new selection date.
     */
    private fun changeSelectionDate(date: LocalDate) {
        viewModelScope.launch {
            getTasksUseCase.changeSelectionDate(date)
        }
    }

    /**
     * Changes the sorting type for tasks.
     * @param sortType The new sorting type or `null` to clear sorting.
     */
    private fun changeSortType(sortType: SortType?) {
        viewModelScope.launch {
            getTasksUseCase.changeSortType(sortType)
        }
    }

    /**
     * Adds a new task to the tasks repository.
     * @param task The task to be added.
     */
    private fun addTask(task: Task) {
        viewModelScope.launch {
            addTaskUseCase.addTask(task)
        }
    }

    /**
     * Deletes a task from the tasks repository.
     * @param taskId The ID of the task to be deleted.
     */
    private fun deleteTask(taskId: Long) {
        viewModelScope.launch {
            deleteTaskUseCase.deleteTask(taskId)
        }
    }

    /**
     * Updates the search text used for filtering tasks.
     * @param text The new search text.
     */
    private fun changeTaskSearchText(text: String) {
        viewModelScope.launch {
            getTasksUseCase.changeTaskSearchText(text)
        }
    }

    /**
     * Fetches all reminders.
     * @return A [Flow] emitting a [ResultContainer] containing a list of all task reminders.
     */
    private fun collectReminders() {
        viewModelScope.launch {
            getRemindersUseCase.getAllReminders().collect {
                _reminders.value = it
            }
        }
    }

    /**
     * Collects not completed tasks with a specific date.
     */
    private fun collectNotCompletedTasksWithDate() {
        viewModelScope.launch {
            getTasksUseCase.getNotCompletedTasksForDate().collect {
                _notCompletedTasksWithDate.value = it
            }
        }
    }

    /**
     * Collects completed tasks with a specific date.
     */
    private fun collectCompletedTasksWithDate() {
        viewModelScope.launch {
            getTasksUseCase.getCompletedTasksForDate().collect {
                _completedTasksWithDate.value = it
            }
        }
    }

    /**
     * Fetches the currently selected sorting type.
     * @return A flow emitting a [ResultContainer] with the selected [SortType].
     */
    private fun collectSelectedSortType() {
        viewModelScope.launch {
            getTasksUseCase.getSelectedSortType().collect {
                _sortType.value = it
            }
        }
    }

    /**
     * Fetches the currently selected category ID.
     * @return A [Flow] emitting a [ResultContainer] with the selected category ID.
     */
    private fun collectSelectedCategoryId() {
        viewModelScope.launch {
            getCategoriesUseCase.getSelectedCategoryId().collect {
                _activeCategoryId.value = it
            }
        }
    }

    /**
     * Fetches tasks with deadlines before the current date.
     * @return A [Flow] emitting a [ResultContainer] containing a list of previous tasks.
     */
    private fun collectPreviousTasks() {
        viewModelScope.launch {
            getTasksUseCase.getPreviousTasks().collect {
                _previousTasks.value = it
            }
        }
    }

    /**
     * Fetches tasks with deadlines today.
     * @return A [Flow] emitting a [ResultContainer] containing a list of today's tasks.
     */
    private fun collectTodayTasks() {
        viewModelScope.launch {
            getTasksUseCase.getTodayTasks().collect {
                _todayTasks.value = it
            }
        }
    }

    /**
     * Fetches tasks with deadlines in the future.
     * @return A [Flow] emitting a [ResultContainer] containing a list of future tasks.
     */
    private fun collectFutureTasks() {
        viewModelScope.launch {
            getTasksUseCase.getFutureTasks().collect {
                _futureTasks.value = it
            }
        }
    }

    /**
     * Fetches tasks that are completed.
     * @return A [Flow] emitting a [ResultContainer] containing a list of completed tasks.
     */
    private fun collectCompletedTasks() {
        viewModelScope.launch {
            getTasksUseCase.getCompletedTasks().collect {
                _completedTasks.value = it
            }
        }
    }

    /**
     * Fetches all available task categories.
     * @return A [Flow] emitting a [ResultContainer] containing a list of task categories.
     */
    private fun collectCategories() {
        viewModelScope.launch {
            getCategoriesUseCase.getCategories().collect {
                _categories.value = it
            }
        }
    }

    /**
     * Changes the selected category by its ID.
     * @param categoryId The new category ID or `null` to clear the selection.
     */
    private fun changeCategory(categoryId: Long?) {
        viewModelScope.launch {
            getTasksUseCase.changeCategory(categoryId)
        }
    }

    /**
     * Creates a new task category.
     * @param name The name of the new category.
     */
    private fun addCategory(name: String) {
        viewModelScope.launch {
            addCategoryUseCase.addCategory(name)
        }
    }

    /**
     * Deletes a task category.
     * @param categoryId The ID of the category to be deleted.
     */
    private fun deleteCategory(categoryId: Long) {
        viewModelScope.launch {
            deleteCategoryUseCase.deleteCategory(categoryId)
        }
    }

    /**
     * Factory for creating instances of [TasksViewModel] with assisted injection.
     */
    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val addTaskUseCase: AddTaskUseCase,
        private val updateTaskUseCase: UpdateTaskUseCase,
        private val deleteTaskUseCase: DeleteTaskUseCase,
        private val getTasksUseCase: GetTasksUseCase,
        private val getCategoriesUseCase: GetCategoriesUseCase,
        private val addCategoryUseCase: AddCategoryUseCase,
        private val deleteCategoryUseCase: DeleteCategoryUseCase,
        private val getRemindersUseCase: GetRemindersUseCase,
        private val createReminderUseCase: CreateReminderUseCase,
        private val deleteReminderUseCase: DeleteReminderUseCase,
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
                deleteCategoryUseCase,
                getRemindersUseCase,
                createReminderUseCase,
                deleteReminderUseCase
            ) as T
        }
    }
}