package com.example.myweatherapp.Services.notifications

import android.app.Notification
import androidx.annotation.DrawableRes

/**
 * Interface for creating notifications in the app
 */

interface NotificationService {
    fun postNewsNotification(title: String, content: String)
    fun postNewsNotificationWithPicture(title: String, content: String, @DrawableRes resId: Int)
    fun createNotification(title: String, content: String): Notification
}