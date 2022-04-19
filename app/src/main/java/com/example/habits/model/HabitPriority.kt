package com.example.habits.model

import com.example.habits.R

enum class HabitPriority(val resId: Int) {
    LOW(R.string.low_priority),
    MEDIUM(R.string.medium_priority),
    HIGH(R.string.high_priority)
}