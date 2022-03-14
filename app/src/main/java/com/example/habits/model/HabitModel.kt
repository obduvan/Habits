package com.example.habits.model

data class HabitModel(
    val id: Int,
    val name: String,
    val description: String,
    val color: Int,
    val interval: Long,
    val type: HabitType,
    val priority: HabitPriority,
)
