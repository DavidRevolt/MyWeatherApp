package com.example.myweatherapp.sync.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkerParameters
import com.example.myweatherapp.R
import com.example.myweatherapp.data.utils.Synchronizer
import com.example.myweatherapp.data.weatherRepository.WeatherRepository
import com.example.myweatherapp.services.notifications.NotificationService
import com.example.myweatherapp.sync.SyncConstraints
import com.example.myweatherapp.sync.syncForegroundInfo
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import java.time.LocalTime
import java.util.concurrent.TimeUnit

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val weatherRepository: WeatherRepository,
    private val notificationService: NotificationService
) : CoroutineWorker(context, workerParams), Synchronizer {


    /*
    * Any ListenableWorker must implement the getForegroundInfo method if you would like to request
    * that the task run as an expedited job.
    */
    override suspend fun getForegroundInfo(): ForegroundInfo =
        context.syncForegroundInfo()

    override suspend fun doWork(): Result = coroutineScope {
        val syncedSuccessfully = awaitAll(async { weatherRepository.sync() }).all { it }
        if (syncedSuccessfully) {
            notificationService.postNewsNotification(
                context.getString(R.string.weatherapp_synced_successfully),
                context.getString(R.string.at)+" ${LocalTime.now()}")
            Result.success()
        } else {
            notificationService.postNewsNotification(
                context.getString(R.string.weatherapp_didnt_synced),
                context.getString(R.string.at)+" ${LocalTime.now()}")
            Result.retry()
        }
    }

    companion object {
        /**
         * For one time work to sync data on app startup use OneTimeWorkRequestBuilder<SyncWorker>()
         * For Periodic work use: PeriodicWorkRequestBuilder<DelegatingWorker>(20, TimeUnit.MINUTES)
         * The minimum repeat interval that can be defined is 15 minutes
         */
        fun startUpSyncWork(): PeriodicWorkRequest {
            return PeriodicWorkRequestBuilder<DelegatingWorker>(5, TimeUnit.HOURS)
                //cant setExpedited in PeriodicRequest
                //.setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                .setConstraints(SyncConstraints)
                .setInputData(SyncWorker::class.delegatedData())
                .build()
        }
    }
}

