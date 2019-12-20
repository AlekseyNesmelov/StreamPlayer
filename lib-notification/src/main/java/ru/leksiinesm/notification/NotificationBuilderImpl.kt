package ru.leksiinesm.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import ru.leksiinesm.lib_notification.R

// TODO draft
class NotificationBuilderImpl : NotificationBuilder {

    override fun build(context: Context): Notification {
        // Create an explicit intent for an Activity in your app
        /* val intent = Intent(context, Ma::class.java).apply {
             flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
         }
         val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)*/

        val channelId =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel(context)
            } else {
                EMPTY_CHANNEL_ID
            }
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val expandedView = RemoteViews(context.packageName, R.layout.player_notificaton)
            val collapsedView = RemoteViews(context.packageName, R.layout.player_notificaton)
            Notification.Builder(context, channelId)
                .setCustomContentView(collapsedView)
                .setCustomBigContentView(expandedView)
                .setContentTitle("Comedy Radio")
                .build()
        } else {
            Notification.Builder(context)
                // .setCustomContentView(collapsedView)
                //.setCustomBigContentView(expandedView)
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