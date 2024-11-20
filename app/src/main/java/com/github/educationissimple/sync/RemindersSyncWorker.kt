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

/**
 * Worker responsible for synchronizing and cleaning up reminders.
 *
 * Cancels any reminders that are scheduled for past dates using the [ReminderScheduler].
 *
 * The worker runs in the background and is scheduled by the WorkManager.
 */
class RemindersSyncWorker @Inject constructor(
    private val taskRepository: TasksRepository,
    private val reminderScheduler: ReminderScheduler,
    context: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {

    /**
     * Performs the work of synchronizing reminders.
     *
     * This method retrieves all non-pending reminders from the repository, checks
     * if they are outdated (before today's date), and deletes or cancels them accordingly.
     * If a reminder's date is in the past, it is removed from the repository and its
     * associated scheduled task is canceled.
     *
     * @return Result.success() if the operation is successful, or Result.failure() if an error occurs.
     */
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

    /**
     * Factory class to create instances of the RemindersSyncWorker.
     *
     * This factory ensures that the [RemindersSyncWorker] is properly initialized with
     * the necessary dependencies (task repository and reminder scheduler) when created
     * by WorkManager.
     */
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
        /**
         * Tag used for identifying the RemindersSyncWorker in the WorkManager.
         */
        const val TAG = "RemindersSyncWorker"
    }

}