package com.anil.definelabsdemo.utils

import androidx.room.TypeConverter
import com.anil.definelabsdemo.models.AllMatchedResponse
import com.anil.definelabsdemo.models.MatchResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConverter {

    val gson = Gson()
    @TypeConverter
    fun matchToString(recipe: MatchResponse): String {
        return gson.toJson(recipe)
    }

    @TypeConverter
    fun stringToMatch(recipeString: String): MatchResponse {
        val objectType = object : TypeToken<MatchResponse>() {}.type
        return gson.fromJson(recipeString, objectType)
    }

}