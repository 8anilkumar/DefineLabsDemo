package com.anil.definelabsdemo.utils

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.anil.definelabsdemo.models.Venue

@Database(entities = [Venue::class], exportSchema = false, version = 1)
abstract class DatabaseHandler : RoomDatabase() {
    abstract fun matchInterface(): MatchDao?
}