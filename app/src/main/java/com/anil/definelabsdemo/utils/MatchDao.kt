package com.anil.definelabsdemo.utils

import androidx.room.*
import com.anil.definelabsdemo.models.Venue

@Dao
interface MatchDao {

    @Query("SELECT * FROM venue_table")
    fun getAllMatch(): List<Venue>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllMatched(addMatched: Venue)

    @Delete
    fun deleteFavoriteVenue(venue: Venue)
}