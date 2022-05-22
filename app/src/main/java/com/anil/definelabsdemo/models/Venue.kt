package com.anil.definelabsdemo.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.anil.definelabsdemo.utils.Constants

@Entity(tableName = Constants.VENUE_TABLE)
data class Venue(
    val id: String,
    val name: String,
    val isStarred: Boolean){
    @PrimaryKey(autoGenerate = true)
    var venueId: Int=0
}