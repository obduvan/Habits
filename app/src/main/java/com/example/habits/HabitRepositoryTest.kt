package com.example.habits

import android.graphics.Color
import com.example.habits.model.HabitModel
import com.example.habits.model.HabitPriority
import com.example.habits.model.HabitType


object HabitRepositoryTest {
    private var habitsList: MutableList<HabitModel>

    init {
        habitsList = getRandomHabits()
//        habitsList = ArrayList()
    }

    fun getHabits(): List<HabitModel> = habitsList

    fun getHabit(position: Int): HabitModel? {
        if (position in 0 until habitsList.size){
            return habitsList[position]
        }
        return null
    }


    fun updateHabit(position: Int, habitModel: HabitModel?) {
        if (!isValidPosition(position) || habitModel == null) {
            return
        }
        habitsList = ArrayList(habitsList)
        habitsList[position] = habitModel
    }

    fun deleteHabit(position: Int) {
        if (!isValidPosition(position)) {
            return
        }
        habitsList = ArrayList(habitsList)
        habitsList.removeAt(position)
    }

    fun addHabit(habitModel: HabitModel?) {
        if (habitModel == null) {
            return
        }
        habitsList = ArrayList(habitsList)
        habitsList.add(0, habitModel)
    }


    private fun isValidPosition(position: Int): Boolean {
        return position in 0 until habitsList.size
    }

    private fun getRandomHabits(): MutableList<HabitModel> {
        val list = mutableListOf<HabitModel>()
        (0..20).forEach {
            val type = if (it % 2 == 0) {
                HabitType.GOOD
            } else {
                HabitType.BAD
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