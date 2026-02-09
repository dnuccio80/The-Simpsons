package com.example.thesimpsons.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson

class ListStringConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromList(list:List<String>?):String {
        return gson.toJson(list?: emptyList<String>())
    }

    @TypeConverter
    fun toList(json:String?):List<String> {
        if(json.isNullOrEmpty()) return emptyList()
        return gson.fromJson(json, Array<String>::class.java).toList()
    }

}