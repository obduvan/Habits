package com.example.data.network.DTO

import android.util.Log
import com.example.domain.entities.HabitModel
import com.example.domain.entities.HabitPriority
import com.example.domain.entities.HabitType
import com.google.gson.annotations.SerializedName


data class HabitDTO(
    @SerializedName("uid")
    var uid: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("priority")
    val priority: Int,

    @SerializedName("type")
    val type: Int,

    @SerializedName("count")
    val count: Int,
    @SerializedName("frequency")
    val frequency: Int,

    @SerializedName("color")
    val color: Int,

    @SerializedName("date")
    val date: Int,

    @SerializedName("done_dates")
    val doneDates: List<Int>,
) {
    companion object {
        fun fromHabitModel(habit: HabitModel) = HabitDTO(
            uid = habit.id,
            title = habit.name,
            description = habit.description,
            priority = habit.priority.ordinal,
            type = habit.type.ordinal,
            count = habit.countRepeats,
            frequency = habit.interval,
            color = habit.color,
            date = habit.date,
            doneDates = habit.doneDates
        )
    }

    fun toHabitModel(): HabitModel {
        return HabitModel(
            id = uid,
            name = title,
            description = description,
            color = color,
            date = date,
            countRepeats = count,
            interval = frequency,
            type = HabitType.createPriority(type),
            priority = HabitPriority.createPriority(priority),
            doneDates = doneDates
        )
    }
}

