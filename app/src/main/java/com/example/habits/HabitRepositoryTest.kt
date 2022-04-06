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

    fun getHabits(type: HabitType) = habitsList.filter { it.type == type }


    fun getHabit(id: Int): HabitModel? {
        if (isValidPosition(id)) {
            return habitsList.find { it.id == id }
        }
        return null
    }

    fun updateHabit(id: Int, habitModel: HabitModel?) {
        if (!isValidPosition(id) || habitModel == null) {
            return
        }
        habitsList = ArrayList(habitsList)

        val position = habitsList.indexOfFirst { it.id == id }
        habitsList[position] = habitModel
    }

    fun deleteHabit(id: Int) {
        if (!isValidPosition(id)) {
            return
        }
        habitsList = ArrayList(habitsList)
        habitsList.removeIf { it.id == id }
    }

    fun addHabit(habitModel: HabitModel?) {
        if (habitModel == null) {
            return
        }
        habitModel.id = habitsList.size
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
                name = it.toString(),
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