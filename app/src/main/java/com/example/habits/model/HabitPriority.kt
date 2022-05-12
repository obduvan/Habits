package com.example.habits.model

import com.example.habits.R

enum class HabitPriority(val resId: Int) {
    LOW(R.string.low_priority),
    MEDIUM(R.string.medium_priority),
    HIGH(R.string.high_priority);

    companion object {
        fun createPriority(ordinal: Int): HabitPriority {
            return when (ordinal) {
                0 -> LOW
                1 -> MEDIUM
                2 -> HIGH
                else -> throw IllegalStateException("Unknown ordinal of enum class.")
            }
        }
    }
}