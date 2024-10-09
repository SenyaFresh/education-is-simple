package com.github.common_impl

import android.util.Log
import com.github.common.Logger

/**
 * Android implementation of [Logger].
 */
class AndroidLogger: Logger {

    /**
     * Log message to logcat.
     */
    override fun log(message: String) {
        Log.d("AndroidLogger", message)
    }

    /**
     * Log message and error to logcat.
     */
    override fun logError(exception: Throwable, message: String?) {
        Log.d("AndroidLogger", message, exception)
    }

}