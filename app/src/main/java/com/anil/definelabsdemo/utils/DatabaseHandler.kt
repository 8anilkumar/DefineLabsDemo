package com.anil.definelabsdemo.utils

import androidx.annotation.NonNull
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.anil.definelabsdemo.models.AllMatchedResponse

@Database(entities = [AllMatchedResponse::class], exportSchema = false, version = 1)
@TypeConverters(TypeConverter::class)
abstract class DatabaseHandler : RoomDatabase() {
    abstract fun matchInterface(): MatchDao?
}