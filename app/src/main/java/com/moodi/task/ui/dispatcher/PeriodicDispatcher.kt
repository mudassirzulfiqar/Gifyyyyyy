package com.moodi.task.ui.dispatcher

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * This class is used to run a task periodically as required in the task.
 * runPeriodicTask() is used to run the task periodically with the given interval as
 * [PeriodicDispatcher.INTERVAL]
 * stop() is used to stop the task
 */
class PeriodicDispatcher(private val dispatcher: CoroutineDispatcher) {
    private var isTaskRunning = false

    fun runPeriodicTask(scope: CoroutineScope, task: suspend () -> Unit) {
        if (!isTaskRunning) {
            isTaskRunning = true
            scope.launch(dispatcher) {
                while (isTaskRunning) {
                    task()
                    delay(INTERVAL)
                }
            }
        }
    }

    /**
     * This method is used to stop the task
     * It is not important to call this method as coroutine context is cancelled when the viewmodel is
     * destroyed. But it is good to have this method to stop the task when required.
     *
     * This method helps in testing the code.
     */
    fun stop() {
        isTaskRunning = false
    }

    companion object {
        const val INTERVAL = 10000L
    }
}
