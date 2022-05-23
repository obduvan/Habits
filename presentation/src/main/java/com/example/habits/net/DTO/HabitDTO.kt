package com.example.habits.net.DTO

import com.example.habits.model.HabitModel
import com.example.habits.model.HabitPriority
import com.example.habits.model.HabitType

data class HabitDTO(
    var uid: String,
    val title: String,
    val description: String,
    val priority: Int,
    val type: Int,
    val count: Int,
    val frequency: Int,
    val color: Int,
    val date: Int,
    val doneTask: List<Int> = listOf()
) {
    companion object {
        fun fromHabitModel(habit: HabitModel) = HabitDTO(
            uid = habit.id,
            title = habit.name,
            description = habit.description,
            priority = habit.priority.ordinal,
            type = habit.type.ordinal,
            count = habit.countRepeats,
            frequency = habit.interval,
            color = habit.color,
            date = habit.date
        )
    }

    fun toHabitModel() = HabitModel(
        id = uid,
        name = title,
        description = description,
        color = color,
        date = date,
        countRepeats = count,
        interval = frequency,
        type = HabitType.createPriority(type),
        priority = HabitPriority.createPriority(priority)

    )
}

