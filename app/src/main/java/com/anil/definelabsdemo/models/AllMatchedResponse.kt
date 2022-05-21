package com.anil.definelabsdemo.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MATCH_TABLE")
data class AllMatchedResponse(val response: MatchResponse){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
// table - model -> mofdelLisy-> venu