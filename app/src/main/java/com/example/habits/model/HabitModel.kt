package com.example.habits.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class HabitModel(
    var id: Int,
    var name: String,
    var description: String,
    var color: Int,
    var countRepeats: Int,
    var interval: Int,
    var type: HabitType,
    var priority: HabitPriority,
) : Parcelable
