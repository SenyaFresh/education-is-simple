package com.github.educationissimple.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.github.educationissimple.common.unwrapFirstNotPending
import com.github.educationissimple.glue.tasks.mappers.toReminderItem
import com.github.educationissimple.notifications.schedulers.ReminderScheduler
import com.github.educationissimple.tasks.domain.repositories.TasksRepository
import java.time.LocalDate
import javax.inject.Inject

class RemindersSyncWorker @Inject constructor(
    private val taskRepository: TasksRepository,
    private val reminderScheduler: ReminderScheduler,
    context: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {

        val reminders = taskRepository.getAllReminders().unwrapFirstNotPending()

        val today = LocalDate.now()
        reminders.forEach { reminder ->
            if (reminder.datetime.toLocalDate().isBefore(today)) {
                reminderScheduler.cancel(reminder.toReminderItem())
                taskRepository.deleteReminder(reminder)
            }
        }

        return Result.success()
    }

    class Factory @Inject constructor(
        private val taskRepository: TasksRepository,
        private val reminderScheduler: ReminderScheduler,
    ): WorkerFactory() {

        override fun createWorker(
            appContext: Context,
            workerClassName: String,
            workerParameters: WorkerParameters
        ): ListenableWorker? {
            return if (workerClassName == RemindersSyncWorker::class.java.name) {
                RemindersSyncWorker(taskRepository, reminderScheduler, appContext, workerParameters)
            } else {
                null
            }
        }
    }

    companion object {
        const val TAG = "RemindersSyncWorker"
    }

}