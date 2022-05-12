package com.example.habits.repository

import androidx.lifecycle.LiveData
import com.example.habits.model.HabitModel
import com.example.habits.model.HabitUid
import com.example.habits.net.ApiResponse

interface IHabitRepository {

    val habits: LiveData<List<HabitModel>>

    suspend fun loadHabits():ApiResponse<Unit>

    fun getHabit(id: String): LiveData<HabitModel>

    suspend fun saveHabit(habit: HabitModel, isNewHabit: Boolean): ApiResponse<HabitUid>

    suspend fun deleteHabit(habit: HabitModel)
}
