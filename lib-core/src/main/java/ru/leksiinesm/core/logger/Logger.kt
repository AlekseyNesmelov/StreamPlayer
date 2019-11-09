package ru.leksiinesm.core.logger

import android.util.Log

class Logger {
    companion object {
        fun d(tag: String? = null, message: String? = null, throwable: Throwable? = null) = Log.d(tag, message, throwable)
    }
}