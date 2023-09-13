package com.example.myweatherapp.data.utils

import android.util.Log
import com.example.myweatherapp.model.Weather
import java.util.concurrent.CancellationException

/**
 * Interface marker for a class that manages synchronization between local data and a remote
 * source for a [Syncable].
 */
interface Synchronizer {
    /**
     * Every class extending Synchronizer can call sync() to activate syncWith(synchronizer) while omitting the synchronizer argument
     * Syntactic sugar to call [Syncable.syncWith] while omitting the synchronizer argument
     */
    suspend fun Syncable.sync() = this@sync.syncWith(this@Synchronizer)
}

/**
 * Interface marker for a class that is synchronized with a remote source. Syncing must not be
 * performed concurrently and it is the [Synchronizer]'s responsibility to ensure this.
 */
interface Syncable {
    /**
     * Synchronizes the local database backing the repository with the network.
     * Returns if the sync was successful or not.
     */
    suspend fun syncWith(synchronizer: Synchronizer): Boolean
}

/**
 * Utility function for syncing a repository with the network.
 * [changeListFetcher] Fetches the list for the model that we want to change
 * [modelUpdater] Updates models by consuming the ids from [changeListFetcher]
 *
 */
suspend fun Synchronizer.changeListSync(
    changeListFetcher: suspend () -> List<Weather>, // Direct Function call that returns the List Of outdated weather
    modelUpdater: suspend (List<Int>) -> Unit, // Direct Functions call that fetch new weather from server and than save them
) = suspendRunCatching {
    // convert the list of outdated weather to list of their id's
    val changeList = changeListFetcher().map { it.weatherId }
    Log.d("AppLog","${changeList.size}")
    if (changeList.isEmpty()){
        Log.d("AppLog", "Everything is up to date!")
        return@suspendRunCatching true
    }
    modelUpdater(changeList) //
}.isSuccess


/**
 * Attempts [block], returning a successful [NetworkResponse] if it succeeds, otherwise a [NetworkResponse.Failure]
 * taking care not to break structured concurrency
 */
private suspend fun <T> suspendRunCatching(block: suspend () -> T): Result<T> = try {
    Result.success(block())
} catch (cancellationException: CancellationException) {
    throw cancellationException
} catch (exception: Exception) {
    Log.i(
        "AppLog",
        "Exception in suspendRunCatchingBlock: ${exception.message} Returning failure Result",
        exception,
    )
    Result.failure(exception)
}

