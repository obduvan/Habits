package com.example.habits

import android.graphics.Color
import com.example.habits.model.HabitModel
import com.example.habits.model.HabitPriority
import com.example.habits.model.HabitType


class HabitRepositoryTest {
    companion object {
        fun getHabits(): List<HabitModel> {
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
                    description = "My best habit!",
                    color = Color.CYAN,
                    interval = (it* 2).toLong(),
                    type = type,
                    priority = HabitPriority.Low
                )
                list.add(habit)
            }
            return list
        }
    }
}