package com.example.habits

import android.graphics.Color
import com.example.habits.model.HabitModel
import com.example.habits.model.HabitPriority
import com.example.habits.model.HabitType
import java.util.ArrayList


class HabitRepositoryTest {

    private var habitsList: MutableList<HabitModel>

    init {
        habitsList = getRandomHabits()
    }

    fun getHabits(): List<HabitModel> = habitsList

    fun getHabit(position: Int) = habitsList[position]

    fun addHabit(position: Int, habitModel: HabitModel) {
        habitsList = ArrayList(habitsList)
        habitsList.add(position, habitModel)
    }

    fun changeHabit(position: Int, habitModel: HabitModel) {
        habitsList = ArrayList(habitsList)
        habitsList[position] = habitModel
    }

    fun deleteHabit(position: Int) {
        habitsList = ArrayList(habitsList)
        habitsList.removeAt(position)
    }

    fun addHabit(habitModel: HabitModel) {
        habitsList = ArrayList(habitsList)
        habitsList.add(0, habitModel)
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
                color = Color.BLUE,
                interval = 31233122,
                type = type,
                priority = HabitPriority.High
            )
            list.add(habit)
        }
        return list
    }
}