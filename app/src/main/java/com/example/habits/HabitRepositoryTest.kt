package com.example.habits

import android.graphics.Color
import com.example.habits.model.HabitModel
import com.example.habits.model.HabitPriority
import com.example.habits.model.HabitType


interface OnHabitRepositoryListener{
    fun habitsUpdate(newHabits: List<HabitModel>)
}

class HabitRepositoryTest(private val habitsUpdateListener: OnHabitRepositoryListener) {

    private var habitsList: MutableList<HabitModel>

    init {
//        habitsList = getRandomHabits()
        habitsList = ArrayList()
    }

    fun getHabits(): List<HabitModel> = habitsList

    fun getHabit(position: Int) = habitsList[position]

    fun addHabit(position: Int, habitModel: HabitModel) {
        habitsList = ArrayList(habitsList)
        habitsList.add(position, habitModel)

        invokeListener()
    }

    fun changeHabit(position: Int, habitModel: HabitModel) {
        habitsList = ArrayList(habitsList)
        habitsList[position] = habitModel

        invokeListener()
    }

    fun deleteHabit(position: Int) {
        habitsList = ArrayList(habitsList)
        habitsList.removeAt(position)

        invokeListener()
    }

    fun addHabit(habitModel: HabitModel) {
        habitsList = ArrayList(habitsList)
        habitsList.add(0, habitModel)

        invokeListener()
    }

    private fun invokeListener(){
        habitsUpdateListener.habitsUpdate(habitsList)
    }

    private fun getRandomHabits(): MutableList<HabitModel> {
        val list = mutableListOf<HabitModel>()
        (0..20).forEach {
            val type = if (it % 2 == 0) {
                HabitType.Good
            } else {
                HabitType.Bad
            }
            val habit = HabitModel(
                id = it,
                name = "Habit",
                countRepeats = 1213,
                description = "My best habit!",
                color = Color.RED,
                interval = 31233122,
                type = type,
                priority = HabitPriority.High
            )
            list.add(habit)
        }
        return list
    }
}