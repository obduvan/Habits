package com.example.habits.utils

import com.example.domain.entities.HabitPriority
import com.example.domain.entities.HabitType
import com.example.habits.R

class ResourceUtils {
    companion object {
         fun getResourcePriority(priority: HabitPriority): Int {
            return when (priority) {
                HabitPriority.LOW -> R.string.low_priority
                HabitPriority.MEDIUM -> R.string.medium_priority
                HabitPriority.HIGH -> R.string.high_priority
            }
        }

         fun getResourceType(type: HabitType): Int {
            return when (type) {
                HabitType.GOOD -> R.string.habit_type_good
                HabitType.BAD -> R.string.habit_type_bad
            }
        }
    }
}