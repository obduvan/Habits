package com.example.habits.model

import android.content.Context
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.habits.room.HabitEntity
import kotlinx.parcelize.Parcelize

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
























