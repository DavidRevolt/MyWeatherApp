package com.example.myweatherapp.data.utils

import androidx.room.TypeConverter
import java.time.Instant

class DatabaseConverters {
    @TypeConverter
    fun longToInstant(value: Long?): Instant? =
        value?.let(Instant::ofEpochSecond)

    @TypeConverter
    fun instantToLong(instant: Instant?): Long? =
        instant?.epochSecond //MUST USE SECONDS Not milli!! , sqlite cant compare timestamps in milliseconds!!!!
}