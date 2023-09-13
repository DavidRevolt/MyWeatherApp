package com.example.myweatherapp.services.notifications

import android.app.Notification
import android.app.Notification.CATEGORY_SERVICE
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import androidx.activity.ComponentActivity
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.myweatherapp.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


/**
 * Displays notifications in the system tray.
 * Displays only if user have Manifest.permission.POST_NOTIFICATIONS permission
 *
 * Example calls:
 * notificationService.showNotification("Title", "Content")
 * notificationService.showNotificationWithPicture("Title2", "Content2", R.drawable.wallpaper2)
 */

class NotificationServiceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : NotificationService {
    private val notificationChannel = NotificationChannel(
        "app_notification",
        "Notifications",
        NotificationManager.IMPORTANCE_HIGH
    )
    private val notificationManager =
        context.getSystemService(ComponentActivity.NOTIFICATION_SERVICE) as NotificationManager

    init {
        notificationManager.createNotificationChannel(notificationChannel)
    }

    override fun postNewsNotification(title: String, content: String) {
        if (!NotificationManagerCompat.from(context).areNotificationsEnabled())
            return

        val notification = NotificationCompat.Builder(context, notificationChannel.id)
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(notificationChannel.importance) // we also need to set this here for older platforms
            .setGroup(CATEGORY_SERVICE)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(
            3,
            notification
        )
    }


    override fun postNewsNotificationWithPicture(
        title: String,
        content: String,
        @DrawableRes resId: Int
    ) {
        if (!NotificationManagerCompat.from(context).areNotificationsEnabled())
            return
        val notification = NotificationCompat.Builder(context, notificationChannel.id)
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(notificationChannel.importance) // we also need to set this here for older platforms
            .setGroup(CATEGORY_SERVICE)
            .setAutoCancel(true)
            .setStyle(
                NotificationCompat
                    .BigPictureStyle()
                    .bigPicture(
                        context.bitmapFromResource(
                            resId
                        )
                    )
            )
            .build()
        notificationManager.notify(content.hashCode(), notification)
    }


    private fun Context.bitmapFromResource(
        @DrawableRes resId: Int
    ) = BitmapFactory.decodeResource(
        resources,
        resId
    )


    override fun createNotification(title: String, content: String): Notification =
        NotificationCompat.Builder(context, notificationChannel.id)
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(notificationChannel.importance) // we also need to set this here for older platforms
            .setGroup(CATEGORY_SERVICE)
            .setAutoCancel(true)
            .build()


}
