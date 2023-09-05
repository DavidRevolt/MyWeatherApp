package com.example.myweatherapp.di.servicesModules

import com.example.myweatherapp.Services.notifications.NotificationService
import com.example.myweatherapp.Services.notifications.NotificationServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NotificationServiceModule {
    @Binds
    abstract fun bindsNotificationService(NotificationServiceImp: NotificationServiceImpl): NotificationService
}