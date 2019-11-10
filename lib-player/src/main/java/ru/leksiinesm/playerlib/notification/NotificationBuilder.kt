package ru.leksiinesm.playerlib.notification

import android.app.Notification
import android.content.Context

/**
 * Builder for notifications.
 *
 * @author Alexey Nesmelov
 */
interface NotificationBuilder {

    /**
     * Build player notification using [context]
     */
    fun build(context: Context): Notification
}