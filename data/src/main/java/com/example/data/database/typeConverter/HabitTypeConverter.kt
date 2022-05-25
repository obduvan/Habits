package com.example.data.database.typeConverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class HabitTypeConverter {

    @TypeConverter
    fun  toDateList(value: String): List<Int> {
        val type: Type = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromDateList(list: List<Int>): String {
        return Gson().toJson(list)
    }
}