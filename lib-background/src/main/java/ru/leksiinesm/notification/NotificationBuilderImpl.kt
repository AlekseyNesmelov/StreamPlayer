package ru.leksiinesm.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi

class NotificationBuilderImpl : NotificationBuilder {

    override fun build(context: Context): Notification {
        val channelId =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel(context, CHANNEL_ID, CHANNEL_NAME)
            } else {
                EMPTY_CHANNEL_ID
            }
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(context, channelId)
                .setContentTitle("Comedy Radio")
                .build()
        } else {
            Notification.Builder(context)
                .setContentTitle("Comedy Radio")
                .build()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun createNotificationChannel(context: Context, channelId: String, channelName: String): String {
        val chan = NotificationChannel(
            channelId,
            channelName, NotificationManager.IMPORTANCE_NONE
        )
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }

    companion object {
        private const val CHANNEL_ID = "StreamPlayer channel ID"
        private const val EMPTY_CHANNEL_ID = ""
        private const val CHANNEL_NAME = "StreamPlayer channel"
    }
}