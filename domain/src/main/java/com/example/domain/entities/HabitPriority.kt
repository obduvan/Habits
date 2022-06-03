package com.example.domain.entities


enum class HabitPriority() {
    LOW(),
    MEDIUM(),
    HIGH();

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