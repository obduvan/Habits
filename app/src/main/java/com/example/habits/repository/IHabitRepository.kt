package com.example.habits.repository

import androidx.lifecycle.LiveData
import com.example.habits.model.HabitModel
import com.example.habits.model.HabitType

interface IHabitRepository {

    fun getHabits(): LiveData<List<HabitModel>>

    fun getHabits(type: HabitType): LiveData<List<HabitModel>>

    fun getHabit(id: Int): LiveData<HabitModel>

    fun saveHabit(habit: HabitModel)

    fun deleteHabit(id: Int)
}