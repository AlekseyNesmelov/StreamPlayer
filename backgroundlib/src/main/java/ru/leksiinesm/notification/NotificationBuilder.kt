package ru.leksiinesm.notification

import android.app.Notification
import android.content.Context
import androidx.annotation.RequiresApi

interface NotificationBuilder {

    fun build(context: Context) : Notification

    @RequiresApi(value = 26)
    fun createNotificationChannel(context: Context, channelId: String, channelName: String): String
}