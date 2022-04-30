package com.example.habits.repository

import androidx.lifecycle.LiveData
import com.example.habits.model.HabitModel
import com.example.habits.model.HabitType

interface IHabitRepository {

    val habits: LiveData<List<HabitModel>>

    fun getHabits(type: HabitType): LiveData<List<HabitModel>>

    fun getHabit(id: Int): LiveData<HabitModel>

    suspend fun saveHabit(habit: HabitModel, isNewHabit: Boolean)

    suspend fun updateHabit(habit: HabitModel)

    suspend fun deleteHabit(habit: HabitModel)
}