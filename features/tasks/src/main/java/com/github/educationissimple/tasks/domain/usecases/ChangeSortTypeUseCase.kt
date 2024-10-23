package com.github.educationissimple.tasks.domain.usecases

import com.github.educationissimple.tasks.domain.entities.SortType
import com.github.educationissimple.tasks.domain.repositories.TasksRepository
import javax.inject.Inject

class ChangeSortTypeUseCase @Inject constructor(private val tasksRepository: TasksRepository) {

    suspend fun changeSortType(sortType: SortType?) {
        tasksRepository.changeSortType(sortType)
    }

}