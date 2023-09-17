package com.example.myweatherapp.sync

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.myweatherapp.sync.workers.SyncWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WorkManagerSyncManager @Inject constructor(
    @ApplicationContext private val context: Context,
) : SyncManager {
    private val SyncWorkName = "SyncWorkName"

    override val isSyncing: Flow<Boolean> =
        WorkManager.getInstance(context).getWorkInfosForUniqueWorkFlow(SyncWorkName)
            .map(List<WorkInfo>::anyRunning)
            .conflate()


    override fun requestSync() {
        val workManager = WorkManager.getInstance(context)
        // For one Time Work use: workManager.enqueueUniqueWork(
        // For Periodic work use: workManager.enqueueUniquePeriodicWork
        workManager.enqueueUniquePeriodicWork(
            SyncWorkName, // Identifier name for the job
            // For one Time Work use: ExistingWorkPolicy
            // for Periodic work use: ExistingPeriodicWorkPolicy
            ExistingPeriodicWorkPolicy.KEEP, //
            SyncWorker.startUpSyncWork()
        )
    }
}

private fun List<WorkInfo>.anyRunning() = any { it.state == WorkInfo.State.RUNNING }