package com.anil.definelabsdemo.utils

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.anil.definelabsdemo.models.AllMatchedResponse
import com.anil.definelabsdemo.models.MatchResponse
import com.anil.definelabsdemo.models.Venue

@Dao
interface MatchDao {

    @Query("SELECT * FROM MATCH_TABLE")
    fun getAllMatch(): List<AllMatchedResponse>

    @Insert
    fun addAllMatched(addMatched: AllMatchedResponse)
}