package com.example.habits

import android.graphics.Color
import android.os.Parcelable
import android.text.BoringLayout
import com.example.habits.model.HabitModel
import com.example.habits.model.HabitPriority
import com.example.habits.model.HabitType
import kotlinx.parcelize.Parcelize


interface OnHabitRepositoryListener {
    fun habitsUpdate(newHabits: List<HabitModel>, isScrollUp: Boolean)
}

class HabitRepositoryTest(private val habitsUpdateListener: OnHabitRepositoryListener) {
    private var habitsList: MutableList<HabitModel>

    init {
//        habitsList = getRandomHabits()
        habitsList = ArrayList()
    }

    fun getHabits(): List<HabitModel> = habitsList

    fun getHabit(position: Int) = habitsList[position]


    fun changeHabit(position: Int, habitModel: HabitModel?) {
        if (!isValidPosition(position) || habitModel == null) {
            return
        }
        habitsList = ArrayList(habitsList)
        habitsList[position] = habitModel

        invokeListener()
    }

    fun deleteHabit(position: Int) {
        if (!isValidPosition(position)) {
            return
        }
        habitsList = ArrayList(habitsList)
        habitsList.removeAt(position)

        invokeListener()
    }

    fun addHabit(habitModel: HabitModel?) {
        if (habitModel == null) {
            return
        }
        habitsList = ArrayList(habitsList)
        habitsList.add(0, habitModel)

        invokeListener(isScrollUp = true)
    }

    private fun invokeListener(isScrollUp: Boolean = false) {
        habitsUpdateListener.habitsUpdate(habitsList, isScrollUp)
    }

    private fun isValidPosition(position: Int): Boolean {
        return position in 0 until habitsList.size
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