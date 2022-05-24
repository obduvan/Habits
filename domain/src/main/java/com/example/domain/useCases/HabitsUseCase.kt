package com.example.domain.useCases

import androidx.lifecycle.LiveData
import com.example.domain.ApiResponse
import com.example.domain.entities.HabitModel
import com.example.domain.entities.HabitUid
import com.example.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow

class HabitsUseCase(private val habitRepository: HabitRepository) {

    fun getHabits() = habitRepository.getHabits()

    suspend fun loadHabits(): ApiResponse<Unit> {
        return habitRepository.loadHabits()
    }

     fun getHabit(id: String): Flow<HabitModel> {
        return habitRepository.getHabit(id)
    }

    suspend fun deleteHabit(habit: HabitModel): ApiResponse<Unit> {
        return habitRepository.deleteHabit(habit)
    }

    suspend fun saveHabit(habit: HabitModel, isNewHabit: Boolean): ApiResponse<HabitUid> {
        return habitRepository.saveHabit(habit, isNewHabit)
    }
}