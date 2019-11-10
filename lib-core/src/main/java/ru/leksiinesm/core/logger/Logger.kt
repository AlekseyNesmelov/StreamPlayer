package ru.leksiinesm.core.logger

import android.util.Log

/**
 * Default logger.
 *
 * @author Alexey Nesmelov
 */
class Logger {
    companion object Sender {

        /**
         * Send debug log message.
         */
        fun d(tag: String? = null, message: String? = null, throwable: Throwable? = null) {
            Log.d(tag, message, throwable)
        }

        /**
         * Send error log message.
         */
        fun e(tag: String? = null, message: String? = null, throwable: Throwable? = null) {
            Log.e(tag, message, throwable)
        }
    }
}