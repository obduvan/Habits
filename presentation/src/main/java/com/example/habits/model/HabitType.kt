package com.example.habits.model

import com.example.habits.R

enum class HabitType(val resourceId: Int) {
    GOOD(R.string.habit_type_good), BAD(R.string.habit_type_bad);

    companion object {
        fun createPriority(ordinal: Int): HabitType {
            return when (ordinal) {
                0 -> GOOD
                1 -> BAD
                else -> throw IllegalStateException("Unknown ordinal of enum class.")
            }
        }
    }
}

