package com.example.domain.repository

import com.example.domain.ApiResponse
import com.example.domain.entities.HabitModel
import com.example.domain.entities.HabitUid
import kotlinx.coroutines.flow.Flow

interface HabitRepository {

    fun getHabits(): Flow<List<HabitModel>>

    suspend fun loadHabits(): ApiResponse<Unit>

    fun getHabit(id: String): Flow<HabitModel>

    suspend fun saveHabit(habit: HabitModel, isNewHabit: Boolean): ApiResponse<HabitUid>

    suspend fun deleteHabit(habit: HabitModel): ApiResponse<Unit>
}
