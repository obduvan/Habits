package com.example.domain.entities


enum class HabitType() {
    GOOD(), BAD();

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

