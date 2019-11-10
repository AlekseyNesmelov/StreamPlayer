package ru.leksiinesm.playerlib.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi

// TODO draft
class NotificationBuilderImpl : NotificationBuilder {

    override fun build(context: Context): Notification {
        val channelId =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel(context)
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
    private fun createNotificationChannel(context: Context): String {
        val chan = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME, NotificationManager.IMPORTANCE_NONE
        )
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return CHANNEL_ID
    }

    companion object {
        private const val CHANNEL_ID = "StreamPlayer channel ID"
        private const val EMPTY_CHANNEL_ID = ""
        private const val CHANNEL_NAME = "StreamPlayer channel"
    }
}