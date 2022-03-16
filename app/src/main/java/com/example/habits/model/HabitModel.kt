package com.example.habits.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HabitModel(
    val id: Int,
    val name: String,
    val description: String,
    val color: Int,
    val countRepeats: Int,
    val interval: Int,
    val type: HabitType,
    val priority: HabitPriority,
) : Parcelable
