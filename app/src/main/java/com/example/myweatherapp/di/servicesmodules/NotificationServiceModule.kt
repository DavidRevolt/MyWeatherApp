package com.example.myweatherapp.di.servicesmodules

import com.example.myweatherapp.services.notifications.NotificationService
import com.example.myweatherapp.services.notifications.NotificationServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NotificationServiceModule {
    @Binds
    abstract fun bindsNotificationService(notificationServiceImp: NotificationServiceImpl): NotificationService
}