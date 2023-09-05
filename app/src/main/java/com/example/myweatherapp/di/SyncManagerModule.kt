package com.example.myweatherapp.di

import com.example.myweatherapp.sync.SyncManager
import com.example.myweatherapp.sync.WorkManagerSyncManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SyncManagerModule {
    @Binds
    abstract fun bindsSyncManager(workManagerSyncManager: WorkManagerSyncManager): SyncManager
}