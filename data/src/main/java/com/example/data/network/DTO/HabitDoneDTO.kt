package com.example.data.network.DTO

import com.example.domain.entities.HabitModel
import com.google.gson.annotations.SerializedName

data class HabitDoneDTO(
    @SerializedName("date")
    val date: Int,
    @SerializedName("habit_uid")
    val habitUid: String
) {
    companion object{
        fun fromHabitModel(habit: HabitModel, doneDate: Int) = HabitDoneDTO(
            date = doneDate,
            habitUid = habit.id
        )
    }
}
