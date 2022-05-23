package com.example.domain.repository

import androidx.lifecycle.LiveData
import com.example.domain.ApiResponse

interface HabitRepository {

    val habits: LiveData<List<com.example.domain.entities.HabitModel>>

    suspend fun loadHabits(): ApiResponse<Unit>

    fun getHabit(id: String): LiveData<com.example.domain.entities.HabitModel>

    suspend fun saveHabit(habit: com.example.domain.entities.HabitModel, isNewHabit: Boolean): ApiResponse<com.example.domain.entities.HabitUid>

    suspend fun deleteHabit(habit: com.example.domain.entities.HabitModel): ApiResponse<Unit>
}
